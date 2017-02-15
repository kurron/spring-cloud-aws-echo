FROM registry.transparent.com/transparent/oracle-jdk-8:1.8.0_112

MAINTAINER Ron Kurr "rkurr@jvmguy.com"

EXPOSE 8080

# switching to admin level user
USER root

ENTRYPOINT ["java", "-server", "-Xms128m", "-Xmx128m", "-Djava.awt.headless=true",  "-jar", "/opt/echo.jar"]

COPY build/libs/echo-0.0.1-SNAPSHOT.jar /opt/echo.jar
