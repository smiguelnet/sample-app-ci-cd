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
  --tag "172.17.0.1:5000/smiguelnet/sample-app:latest" .

# TAG IMAGE
docker tag "172.17.0.1:5000/smiguelnet/sample-app:latest" "172.17.0.1:5000/smiguelnet/sample-app:$1"

# INSPECT IMAGE
docker image inspect -f '{{json .Config.Labels}}' "172.17.0.1:5000/smiguelnet/sample-app:$1" | python -m json.tool

# PUSH IMAGE
docker image push "172.17.0.1:5000/smiguelnet/sample-app:latest"
docker image push "172.17.0.1:5000/smiguelnet/sample-app:$1"
