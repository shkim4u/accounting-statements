#!/bin/bash

export ACCOUNT_ID=$(aws sts get-caller-identity --output json | jq ".Account" | tr -d '\"')
export AWS_REGION=ap-northeast-2
export REPOSITORY_NAME=mountebank
export IMAGE_URL="${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
export MB_IMAGE_NAME="${IMAGE_URL}/mountebank:latest"
export FT_IMAGE_NAME="${IMAGE_URL}/flipt:latest"

cat docker-compose-template.yaml | envsubst > docker-compose.yaml

