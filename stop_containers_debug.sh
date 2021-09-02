#!/bin/sh

docker-compose -f docker-compose.yaml stop featuretoggle mountebank flyway db
