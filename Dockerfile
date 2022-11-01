FROM java:openjdk-8u111-jdk
WORKDIR /opt
ADD target/nounou-api-*.jar nounou-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/nounou-api.jar"]
