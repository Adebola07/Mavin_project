FROM alpine:3.14
WORKDIR /home/app
COPY ./target/java-maven-app-1.1.0-SNAPSHOT.jar  /home/app
EXPOSE 8083
CMD ["docker"]
