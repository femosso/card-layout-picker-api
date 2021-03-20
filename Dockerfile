FROM alpine:3.12

# install OpenJDK 11
RUN apk --no-cache add openjdk8

# copy the compiled spring boot application
ADD target/card-layout-picker-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENV _JAVA_OPTIONS "-Xms512m -Xmx2g"

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]