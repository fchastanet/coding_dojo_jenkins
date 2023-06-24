FROM jenkins/jenkins:2.361.2

ENV JENKINS_USER admin
ENV JENKINS_PASS admin

# allows to skip Jenkins setup wizard
ENV JAVA_OPTS \
  -Djenkins.install.runSetupWizard=false \
  -Dhudson.plugins.git.GitSCM.ALLOW_LOCAL_CHECKOUT=true

USER jenkins
SHELL ["/bin/bash", "-o", "pipefail", "-o", "errexit", "-o", "xtrace", "-c"]
RUN \
  plugins=( \
    blueocean \
    docker-plugin \
    docker-workflow \
    filesystem_scm \
    git \
    pipeline-stage-view \
  ) ;\
  jenkins-plugin-cli --plugins "${plugins[@]}"

# Jenkins runs all groovy files from init.groovy.d dir
# use this for creating default admin user
COPY init.groovy.d/default-user.groovy /var/jenkins_home/init.groovy.d/default-user.groovy/

# custom entrypoint to register ssh host key
COPY custom_entrypoint.sh /usr/local/bin/
ENTRYPOINT ["/usr/bin/tini", "--", "/usr/local/bin/custom_entrypoint.sh"]

# additional plugin added as separate run to avoid too much image pull(2023-06-12)
RUN \
  plugins=( \
    email-ext \
  ) ;\
  jenkins-plugin-cli --plugins "${plugins[@]}"

COPY init.groovy.d/smtp.groovy /var/jenkins_home/init.groovy.d/default-user.groovy/

# volume for Jenkins settings
VOLUME /var/jenkins_home
VOLUME /var/log/jenkins
