#!/bin/bash

export AWS_DEFAULT_REGION=ap-northeast-2
export BRANCH_NAME=accounting-statements:main

export MOD_NAME=$(echo $BRANCH_NAME | sed -e "s/\//-/g")
export MOD_NAME=$(echo $MOD_NAME | sed -e "s/\./-/g" | tr '[:upper:]' '[:lower:]')
export MOD_NAME=$(echo "${MOD_NAME:0:23}")
export MOD_NAME=$(echo "${MOD_NAME%-}")

# [2021-09-10] KSH: If you want to use Image Tag, then modify docker_push.sh file accordingly.
#export TAG_NAME=${MOD_NAME} && echo $TAG_NAME
export TAG_NAME=latest && echo $TAG_NAME
./docker_push.sh

export ECR_APP=accounting-statements

aws ecr start-image-scan --repository-name ${ECR_APP} --image-id imageTag=${TAG_NAME} --region ${AWS_DEFAULT_REGION}

export SCAN_STATUS="IN_PROGRESS"

while [ "${SCAN_STATUS}" != "COMPLETE" ];
do
    echo "Sleep for 5 seconds ..."
    python -c "import time; time.sleep(5)"
    export SCAN_STATUS=$(aws ecr describe-image-scan-findings --repository-name ${ECR_APP} --image-id imageTag=${TAG_NAME} --region ${AWS_DEFAULT_REGION} --output json | jq ".imageScanStatus.status" | tr -d '"')
done

export ISSUE_CNT=$(aws ecr describe-image-scan-findings --repository-name ${ECR_APP} --image-id imageTag=${TAG_NAME} --region ${AWS_DEFAULT_REGION} --output json |  jq ".imageScanFindings.findings" | jq length)

if [ $ISSUE_CNT -gt 0 ]; then
    echo "There are ${ISSUE_CNT} issues during scanning an image ..."
    exit $ISSUE_CNT
else
    echo "An ECR scan has completed without any issues ..."
fi

