#!/bin/bash

# Get all docker-compose.yml files
DOCKER_DIRECTORY=$1
DOCKER_COMPOSE_LIST=$(find "$DOCKER_DIRECTORY" -type f -name "[0-9][0-9]-docker-compose*.yml" | sort -V)

# Build docker command
DOCKER_COMMAND="docker compose "
for DOCKER_COMPOSE in $DOCKER_COMPOSE_LIST; do
  DOCKER_COMMAND+="-f $DOCKER_COMPOSE "
done
DOCKER_COMMAND+="up postgresql app"

# Run
echo "> run \"$DOCKER_COMMAND\""
$DOCKER_COMMAND
