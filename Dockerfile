FROM amazoncorretto:11
MAINTAINER AWS Korea Professional Service

ARG JVM_OPS
ENV JVM_OPS=${JVM_OPS}

ADD /build/libs/statements-*.jar executor.jar
ADD /src/main/resources/application-docker.properties application.properties

CMD ["sh", "-c", "java $JVM_OPS -jar executor.jar"]
