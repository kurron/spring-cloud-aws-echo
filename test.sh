#!/bin/bash

CMD="docker run --cpus 1 \
                --interactive \
                --name aws-echo \
                --network host \
                --rm \
                --tty \
                --memory 268435546 \
                --memory-swap 0 \
                --volume /var/run/docker.sock:/var/run/docker.sock \
                springcloudawsecho_echo:latest"
echo $CMD
$CMD
