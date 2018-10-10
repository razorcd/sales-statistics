FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=target/salesstats-cristiand-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} salesstats-cristiand.jar

ENTRYPOINT ["java","-Xmx128m","-jar","/salesstats-cristiand.jar"]
