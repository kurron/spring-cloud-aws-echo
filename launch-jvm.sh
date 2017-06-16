#!/bin/bash

JVM_DNS_TTL=${1:-30}

CMD="${JAVA_HOME}/bin/java \
    -server \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:+ScavengeBeforeFullGC \
    -XX:+CMSScavengeBeforeRemark \
    -XX:+UseSerialGC \
    -XX:MinHeapFreeRatio=20 \
    -XX:MaxHeapFreeRatio=40 \
    -XX:GCTimeRatio=4 \
    -XX:AdaptiveSizePolicyWeight=90
    -Dsun.net.inetaddr.ttl=${JVM_DNS_TTL} \
    -jar $*"

echo ${CMD}
exec ${CMD}
