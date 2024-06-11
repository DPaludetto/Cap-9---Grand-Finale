FROM openjdk:17-alpine

EXPOSE 8080 8000

ENV TZ="America/Sao_Paulo"

WORKDIR /app

COPY ./target/controle-estoque.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
