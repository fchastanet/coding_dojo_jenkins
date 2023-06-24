#!/bin/sh

set -x
(
	# weird, I don't know why
	# we need to wait a little bit that /var/jenkins_home is made available
	sleep 2
	mkdir -p ~/.ssh
	cp /tmp/.ssh/id_rsa ~/.ssh
	chown -R jenkins: ~/.ssh
	chmod 600 ~/.ssh/*
	# remove eventual jenkins-agent known host
	if [ -f ~/.ssh/known_hosts ]; then
		ssh-keygen -R jenkins-agent || true
	fi
)

(
	# wait jenkins-agent ssh daemon to be up
	sleep 10
	ssh-keyscan jenkins-agent >>~/.ssh/known_hosts
) &

exec /usr/local/bin/jenkins.sh "$@"
