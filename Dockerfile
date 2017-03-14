FROM kurron/docker-oracle-jdk-8

MAINTAINER Ron Kurr "kurr@jvmguy.com"

EXPOSE 8080

# switching to admin level user
USER root

ENTRYPOINT ["java", "-server", "-Xms128m", "-Xmx128m", "-Djava.awt.headless=true",  "-jar", "/opt/echo.jar"]

COPY build/libs/echo-0.0.0.RELEASE.jar /opt/echo.jar
