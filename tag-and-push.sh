#!/bin/bash

# use the time as a tag
UNIXTIME=$(date +%s)

# docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
docker tag springcloudawsecho_echo:latest kurron/spring-cloud-aws-echo:latest
docker tag springcloudawsecho_echo:latest kurron/spring-cloud-aws-echo:${UNIXTIME}
docker images

# Usage:  docker push [OPTIONS] NAME[:TAG]
docker push kurron/spring-cloud-aws-echo:latest
docker push kurron/spring-cloud-aws-echo:${UNIXTIME}
