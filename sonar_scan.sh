#!/bin/bash

export BRANCH_NAME=accounting-statements

export MOD_NAME=$(echo $BRANCH_NAME | sed -e "s/\//-/g")
export MOD_NAME=$(echo $MOD_NAME | sed -e "s/\./-/g" | tr '[:upper:]' '[:lower:]')
export MOD_NAME=$(echo "${MOD_NAME:0:23}")
export MOD_NAME=$(echo "${MOD_NAME%-}") && echo $MOD_NAME

export SONAR_URL=http://3.38.98.6:8443
export SONAR_TOKEN=3f8e318451f429cf7641903bbd7295c72e3d92dd
export SONAR_SEARCH_URL=${SONAR_URL}/api/projects
export SRH_RES=$(curl --request GET -u ${SONAR_TOKEN}:  "${SONAR_SEARCH_URL}/search?projects=${MOD_NAME}")
export SRH_RES=$(echo $SRH_RES | jq ".paging.total")

# Create a project in the SonarQube Server if a project doesn't exist
if [ $SRH_RES -eq 0 ]; then curl --request POST -u ${SONAR_TOKEN}:  "${SONAR_SEARCH_URL}/create?name=${MOD_NAME}&project=${MOD_NAME}"; fi

# Run SonarScanner
./gradlew sonarqube -Dsonar.projectKey=${MOD_NAME} -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_TOKEN}

# Quality Gate
#curl -u ${SONAR_TOKEN}: --location --request GET "${SONAR_URL}/api/qualitygates/project_status?projectKey=${MOD_NAME}" > sonar_result.json
#curl -u 3f8e318451f429cf7641903bbd7295c72e3d92dd: --location -X GET "http://3.38.98.6:8443/api/qualitygates/project_status?projectKey=accounting-statements" > sonar_result.json
#curl -u $SONAR_TOKEN: --location -X GET "http://3.38.98.6:8443/api/qualitygates/project_status?projectKey=accounting-statements" > sonar_result.json
curl -u ${SONAR_TOKEN}: --location -X GET "${SONAR_URL}/api/qualitygates/project_status?projectKey=${MOD_NAME}" > sonar_result.json
export SONAR_STATUS=$(cat sonar_result.json| jq ".projectStatus.status" | tr -d '"')

if [ "${SONAR_STATUS}" != "OK" ]; then
  echo "SonarQube Status = ${SONAR_STATUS}"
  exit 1
fi
