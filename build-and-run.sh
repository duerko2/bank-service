#!/bin/bash
set -e
mvn clean package
docker-compose build bank-service
docker-compose up -d bank-service