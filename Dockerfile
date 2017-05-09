FROM kurron/docker-azul-jdk-8:1.8.0_131-b11

MAINTAINER Ron Kurr "kurr@jvmguy.com"

EXPOSE 8080

ENTRYPOINT ["java", "-server", "-XX:MaxRAM=128m", "-XX:+UseSerialGC", "-XX:+ScavengeBeforeFullGC", "-XX:+CMSScavengeBeforeRemark", "-XX:MinHeapFreeRatio=20", "-XX:MaxHeapFreeRatio=40", "-Dsun.net.inetaddr.ttl=60", "-Djava.awt.headless=true",  "-jar", "/opt/echo.jar"]

COPY build/libs/echo-0.0.0.RELEASE.jar /opt/echo.jar
