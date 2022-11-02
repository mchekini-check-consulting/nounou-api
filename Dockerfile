FROM openjdk:17-jdk
WORKDIR /opt
ADD target/nounou-api-*.jar nounou-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/nounou-api.jar"]
