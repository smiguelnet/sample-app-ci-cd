#!/bin/bash
docker build \
  --build-arg ARTIFACT_NAME="sample-app-$1" \
  --label version="$1" \
  --label hash="$(git log -n 1 --pretty=format:'%h')" \
  --label build.date="$(date --iso-8601=seconds)" \
  --label build.author="${CHANGE_AUTHOR}" \
  --label ci.refnumber="${BUILD_NUMBER}" \
  --label ci.executor="${EXECUTOR_NUMBER}" \
  --label ci.node="${NODE_NAME}" \
  --label ci.joburl="${BUILD_URL}" \
  --tag "192.168.1.67:5000/smiguelnet/sample-app:latest" .

# PUSH IMAGE
docker image push "192.168.1.67:5000/smiguelnet/sample-app:latest"

