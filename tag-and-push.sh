#!/bin/bash

# docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
docker tag springcloudawsecho_echo:latest kurron/spring-cloud-aws-echo:latest
docker images

# Usage:  docker push [OPTIONS] NAME[:TAG]
docker push kurron/spring-cloud-aws-echo:latest