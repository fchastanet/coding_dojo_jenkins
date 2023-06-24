import hudson.util.Secret
import hudson.tasks.Mailer.DescriptorImpl
import hudson.plugins.emailext.ExtendedEmailPublisherDescriptor
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration

// Check if enabled
System.getenv()

// Variables
String systemAdminMailAddress = 'admin@coding.dojo.com'
String confSmtpUser = ''
String confSmtpPassword = ''
String confSmtpPort = '1025'
String confSmtpHost = 'mail-catcher'

// Constants
Jenkins instance = Jenkins.instance
DescriptorImpl mailServer = instance.getDescriptor('hudson.tasks.Mailer')
JenkinsLocationConfiguration jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
ExtendedEmailPublisherDescriptor extmailServer =
  instance.getDescriptor('hudson.plugins.emailext.ExtendedEmailPublisher')

Thread.start {
    sleep 5000

    //Jenkins Location
    println '--> Configuring JenkinsLocation'
    jenkinsLocationConfiguration.setAdminAddress(systemAdminMailAddress)
    jenkinsLocationConfiguration.save()

    //E-mail Server
    mailServer.setSmtpAuth(confSmtpUser, confSmtpPassword)
    mailServer.setSmtpHost(confSmtpHost)
    mailServer.setSmtpPort(confSmtpPort)
    mailServer.setCharset('smtp8')

    //Extended-Email
    extmailServer.with {
        smtpAuthUsername = confSmtpUser
        smtpAuthPassword = Secret.fromString(confSmtpPassword)
        smtpHost = confSmtpHost
        smtpPort = confSmtpPort
        charset = 'UTF-8'
        defaultSubject = "\$PROJECT_NAME - Build # \$BUILD_NUMBER - \$BUILD_STATUS!"
        defaultBody = '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:\n\n' +
          'Check console output at $BUILD_URL to view the results.'
    }

    // Save the state
    instance.save()
}
