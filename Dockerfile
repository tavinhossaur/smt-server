# 1. Build (utilizando a imagem alpine da build temurin do OpenJDK 21 por ser mais leve)
from maven:3.9.11-eclipse-temurin-21-alpine as builder
workdir /usr/app

copy pom.xml .
run mvn dependency:go-offline

copy src ./src

run mvn package -DskipTests

# 2. Initializing (utilizando a imagem noble (Ubuntu 24.04 LTS) da build temurin do OpenJDK 21 para ter um container um pouco mais robusto)
from eclipse-temurin:21-jre-noble
workdir /usr/app

copy --from=builder /usr/app/target/*.jar app.jar

expose 8080

entrypoint ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]