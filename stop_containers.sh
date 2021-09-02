#!/bin/sh

docker-compose -f docker-compose.yaml stop statements featuretoggle mountebank flyway db
