# --- estágio 1 (builder) ---
from maven:3.9.11-eclipse-temurin-21-alpine as builder
workdir /app

# copia o arquivo de configuração de dependencias do maven e as instala
copy pom.xml .
run mvn dependency:go-offline

# copia a src e compila a aplicação em um pacote .jar
copy src ./src
run mvn package -DskipTests

# --- estágio 2 (runner) ---
from eclipse-temurin:21-jre-alpine as runner
workdir /app

# instalação do curl para execução de health checks (docker-compose.prod.yml)
run apk add --no-cache curl

# criação de usuário para rodar a aplicação (!= root)
run addgroup -S smtserver && adduser -S -G smtserver runner

# copia o .jar compilado anteriormente e atribui o usuário criado como proprietário
copy --from=builder --chown=runner:smtserver /app/target/*.jar app.jar

# utilza o usuário criado e documenta a porta que a aplicação usará 
user runner
expose 8080

# configurando as opções padrão da JVM, elas são sobrescritas pelos docker composes
env JAVA_OPTS="-XX:+UseZGC -XX:MaxRAMPercentage=80.0"

# executa a aplicação compilada com os parâmetros configurados
entrypoint ["sh", "-c", "exec java ${JAVA_OPTS} -jar app.jar"]