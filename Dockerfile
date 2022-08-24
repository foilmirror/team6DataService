FROM gradle:jdk10 as builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle -b mcc-data-service/build.gradle bootjar

FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ARG LIBS=app/mcc-data-service/build/libs
COPY --from=builder ${LIBS}/ /app/lib
ENTRYPOINT ["java","-jar","./app/lib/mcc-data-service-0.0.1-SNAPSHOT.jar"]