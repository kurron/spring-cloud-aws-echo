FROM kurron/docker-azul-jdk-8:latest

MAINTAINER Ron Kurr "kurr@jvmguy.com"

EXPOSE 8080
ENTRYPOINT ["/home/microservice/launch-jvm.sh", "/opt/echo.jar"]
ADD launch-jvm.sh /home/microservice/launch-jvm.sh
RUN chmod a+x /home/microservice/launch-jvm.sh
COPY build/libs/echo-0.0.0.RELEASE.jar /opt/echo.jar
USER microservice
