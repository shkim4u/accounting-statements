#!/bin/sh

docker-compose -f docker-compose.yaml up -d db flyway mountebank featuretoggle
