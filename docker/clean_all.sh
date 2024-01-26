#!/bin/bash

echo stopping containers...
docker stop $(docker ps -aq) > /dev/null

echo removing containers...
docker rm $(docker ps -aq) > /dev/null

echo removing images...
docker rmi $(docker images -q) > /dev/null

echo -----containers-----
docker ps -a

echo -----images---------
docker images