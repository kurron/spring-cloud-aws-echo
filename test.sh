#!/bin/bash

echo To determine the IP address use docker inspect --format \'{{ .NetworkSettings.IPAddress }}\' aws-echo

CMD="docker run --cpus 1 \
                --interactive \
                --name aws-echo \
                --network bridge \
                --publish-all \
                --name aws-echo \
                --rm \
                --tty \
                --memory 268435546 \
                --memory-swap 0 \
                --volume /var/run/docker.sock:/var/run/docker.sock \
                springcloudawsecho_echo:latest"
echo $CMD
$CMD

