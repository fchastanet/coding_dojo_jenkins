import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey.DirectEntryPrivateKeySource
import hudson.model.Node
import hudson.model.User
import hudson.model.Slave
import hudson.plugins.sshslaves.SSHLauncher
import hudson.slaves.ComputerLauncher
import hudson.slaves.DumbSlave
import hudson.slaves.RetentionStrategy
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy
import hudson.security.HudsonPrivateSecurityRealm
import jenkins.model.Jenkins
import org.jenkinsci.main.modules.sshd.SSHD

class Constants {

    static final short BUILT_IN_NODE_EXECUTORS_NUMBERS = 0
    static final short FIRST_CREDENTIAL_INDEX = 0

}

Map<String,String> env = System.getenv()

Jenkins instance = Jenkins.instance

/* groovylint-disable-next-line Instanceof */
if (!(instance.securityRealm instanceof HudsonPrivateSecurityRealm)) {
    instance.securityRealm = new HudsonPrivateSecurityRealm(false, false, null)
}

println('create new Jenkins user account')
println('username & password from environment variables')
User user = instance.securityRealm.createAccount(env.JENKINS_USER, env.JENKINS_PASS)
user.save()

println('set full control on the new Jenkins user account')
instance.authorizationStrategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.save()

println('add ssh-key')
Domain domain = Domain.global()
credentialProvider = instance.getExtensionList(
        'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
    )[Constants.FIRST_CREDENTIAL_INDEX]

println('retrieve ssh-key content')
String getFileContent(String filename) {
    return new File(filename).text
}
String privateKeyContent = getFileContent('/tmp/.ssh/id_rsa')
DirectEntryPrivateKeySource privateKey = new DirectEntryPrivateKeySource(privateKeyContent)
String privateKeyId = 'agentSshKey'
String privateKeyUserName = 'jenkins'
String privateKeyPassphrase = ''

println('remove credential if exists')
credentials = credentialProvider.credentials
for (credential in credentials) {
    if (credential.descriptor) {
        println('check credential ' + credential.id)
        if (credential.id == privateKeyId) {
            println('remove old credential ' + privateKeyId)
            credentialProvider.store.removeCredentials(domain, credential)
            break
        }
    }
}

println('create credential ' + privateKeyId)
sshKey = new BasicSSHUserPrivateKey(
  CredentialsScope.GLOBAL,
  privateKeyId,
  privateKeyUserName,
  privateKey,
  privateKeyPassphrase,
  'secret used for agent communication'
)
credentialProvider.store.addCredentials(domain, sshKey)
instance.save()

println('Allow connection over ssh')
SSHD sshConfig = instance.getDescriptor('org.jenkinsci.main.modules.sshd.SSHD')
sshConfig.port = 22
sshConfig.save()

class AgentBuilder {

  /**
   * make node launcher
   */
    static ComputerLauncher makeAgentLauncher(String privateKeyId) {
        return new SSHLauncher('jenkins-agent', 22, privateKeyId)
    }

  /**
   * make agent
   */
    static Slave makeAgent(ComputerLauncher launcher) {
        Slave agent = new DumbSlave(
    'main-agent',
    '/var/jenkins_home/agent',
    launcher
  )
        agent.nodeDescription = 'This Agent node allows to launch docker, docker-compose and hadolint commands'
        agent.numExecutors = 2
        agent.labelString = 'docker'
        agent.mode = Node.Mode.NORMAL
        agent.retentionStrategy = new RetentionStrategy.Always()

        return agent
    }

}

println('Set Built-in node number of executors to 0')

instance.numExecutors = Constants.BUILT_IN_NODE_EXECUTORS_NUMBERS
instance.save()

println("Make a 'Permanent Agent'")
launcher = AgentBuilder.makeAgentLauncher(privateKeyId)
agent = AgentBuilder.makeAgent(launcher)
instance.addNode(agent)
instance.save()

println('Disable csrf protection to make jenkins linter work with http')
instance.crumbIssuer = null
instance.save()
