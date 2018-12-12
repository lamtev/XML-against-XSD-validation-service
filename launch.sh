#!/usr/bin/env bash

if [ "$#" -ne 1 ]; then
    echo "Illegal number of arguments!"
    echo "Usage: ./launch.sh <TCP port>"
fi

if [ "$1" -le 0 ]; then
    echo "Port must be positive!"
fi

docker run -p $1:8080 lamtev/xml-against-xsd-validation-service:latest /bin/bash -c "cd /opt/service && gradle runService"
