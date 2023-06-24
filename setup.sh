#!/bin/bash

set -o errexit
set -o pipefail

ROOT_DIR=$(cd "$(readlink -e "${BASH_SOURCE[0]%/*}")" && pwd -P)

Log::displayInfo() {
	echo -e "\e[44mINFO    - ${1}\e[0m" >&2
}

if [[ -d "${ROOT_DIR}/.ssh" ]]; then
	Log::displayInfo "Removing '${ROOT_DIR}/.ssh' directory"
	rm -Rf "${ROOT_DIR}/.ssh" || true
	mkdir -p "${ROOT_DIR}/.ssh"
fi

Log::displayInfo "Pulling necessary docker images"
docker pull dockage/mailcatcher:0.8.2
docker pull alpine:3.18.0
docker pull scrasnups/jenkins-dojo-blueocean:v2
docker pull scrasnups/jenkins-dojo-ssh-agent:v2
docker pull docker:24-dind
docker pull registry:2.8.2

if [[ ! -f "${ROOT_DIR}/.vscode/settings.json" ]]; then
	Log::displayInfo "Copying .vscode/settings.json from template"
	cp -v "${ROOT_DIR}/.vscode/settings.template.json" "${ROOT_DIR}/.vscode/settings.json"
fi

Log::displayInfo "Generating ssh private key for jenkins agent"
docker run --rm -i \
	-v "${ROOT_DIR}/.ssh/:/tmp/.ssh" \
	--entrypoint /usr/bin/env \
	scrasnups/jenkins-dojo-blueocean:v2 \
	ssh-keygen -t rsa -N "" -q -f /tmp/.ssh/id_rsa <<<y >/dev/null 2>&1
chmod 600 "${ROOT_DIR}/.ssh/id_rsa"

Log::displayInfo "Generating ssh public key for jenkins agent"
docker run --rm -it \
	-v "${ROOT_DIR}/.ssh/:/tmp/.ssh" \
	--entrypoint /usr/bin/env \
	scrasnups/jenkins-dojo-blueocean:v2 \
	ssh-keygen -y -f /tmp/.ssh/id_rsa >"${ROOT_DIR}/.ssh/id_rsa.pub"
chmod 755 "${ROOT_DIR}/.ssh/id_rsa.pub"

if [[ ! -f "${ROOT_DIR}/.env" ]]; then
	Log::displayInfo "Generating .env file"
	cp -v "${ROOT_DIR}/.env.template" "${ROOT_DIR}/.env"
fi
