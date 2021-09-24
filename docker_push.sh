#!/bin/bash

export ACCOUNT_ID=$(aws sts get-caller-identity --output json | jq ".Account" | tr -d '\"')
export AWS_REGION=ap-northeast-2
export REPOSITORY_NAME=accounting-statements
export IMAGE_URL="${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
export IMAGE_NAME="${IMAGE_URL}/${REPOSITORY_NAME}:latest"

docker build -t ${IMAGE_NAME} .

aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${IMAGE_URL}

docker push ${IMAGE_NAME}

docker-compose build statements flyway
docker-compose push statements flyway
