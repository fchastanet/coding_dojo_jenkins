#!/bin/bash

set -x
mkdir -p ~/.ssh
cp /tmp/.ssh/id_rsa.pub ~/.ssh/authorized_keys
chown -R jenkins: ~/.ssh
chmod 600 ~/.ssh/*

mkdir -p /var/jenkins_home/agent
chown -R jenkins: /var/jenkins_home

exec setup-sshd "$@"
