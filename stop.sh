#!/bin/bash

COMPOSE_FILE=./docker/docker-compose.yml

echo "🛑 Stopping and removing containers..."
docker-compose -f $COMPOSE_FILE down

echo "✅ All services stopped."
