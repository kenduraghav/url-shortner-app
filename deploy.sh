#!/bin/bash

# Variables
APP_NAME=url-shortner-app
IMAGE_NAME=kenduraghav/url-shortner-app  # Replace with your Docker Hub username
DOCKERFILE_PATH=./Dockerfile
COMPOSE_FILE=./docker/docker-compose.yml

# Build & Push
echo "📦 Building Docker image..."
docker build -t $IMAGE_NAME -f $DOCKERFILE_PATH .

echo "🚀 Pushing to Docker registry..."
docker push $IMAGE_NAME

echo "🧱 Starting services with Docker Compose..."
docker-compose -f $COMPOSE_FILE up -d

echo "✅ Deployment complete!"
