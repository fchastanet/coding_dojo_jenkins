FROM jenkins/ssh-agent:debian-jdk11

USER root
ARG DEBIAN_FRONTEND=noninteractive
COPY 01-noDoc /etc/dpkg/dpkg.cfg.d/
SHELL ["/bin/bash", "-o", "pipefail", "-o", "errexit", "-o", "xtrace", "-c"]
RUN --mount=type=cache,sharing=locked,id=coding-dojo-aptlib,target=/var/cache/apt \
  apt-get update ;\
  apt-get upgrade -y ;\
  apt-get install -y -q --no-install-recommends --no-install-suggests \
    ca-certificates="2021*" \
    curl="7.*" \
    lsb-release="11.*" \
  ;\
  curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
    https://download.docker.com/linux/debian/gpg \
  ;\
  echo "deb [arch=$(dpkg --print-architecture) \
    signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
    https://download.docker.com/linux/debian \
    $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list \
  ;\
  apt-get update ;\
  apt-get install -y -q --no-install-recommends --no-install-suggests \
    docker-buildx-plugin="0.10.*" \
    docker-ce-cli="5:20.*" \
  ;\
  # clean apt packages
  apt-get autoremove -y ;\
  rm -rf /tmp/* /var/tmp/* /usr/share/doc/*

ARG HADOLINT_VERSION=v2.12.0
ARG DOCKER_COMPOSE_VERSION=v2.18.1
RUN \
  # install hadolint
  curl -L "https://github.com/hadolint/hadolint/releases/download/${HADOLINT_VERSION}/hadolint-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/hadolint \
  ;\
  chmod +x /usr/local/bin/hadolint ;\
  \
  # install docker-compose
  mkdir -p /usr/local/lib/docker/cli-plugins ;\
  curl -L "https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/lib/docker/cli-plugins/docker-compose \
  ;\
  chmod +x /usr/local/lib/docker/cli-plugins/docker-compose ;\
  ln -s /usr/local/lib/docker/cli-plugins/docker-compose /usr/local/bin/docker-compose

RUN \
  # change home to match same path as jenkins master
  usermod -d /var/jenkins_home jenkins ;\
  mv /home/jenkins /var/jenkins_home

COPY entrypoint.sh /usr/bin/local/
ENTRYPOINT [ "/usr/bin/local/entrypoint.sh" ]

WORKDIR /var/jenkins_home
USER jenkins
ENV HOME=/var/jenkins_home
ENV JENKINS_AGENT_HOME=/var/jenkins_home
ENV AGENT_WORKDIR=/var/jenkins_home/agent
ENV PATH=$PATH:/usr/local/lib/docker/cli-plugins
