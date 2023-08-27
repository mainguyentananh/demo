FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY . .
RUN mvn install -DskipTests

CMD ["java", "-jar", "/app/target/demo-0.0.1-SNAPSHOT.jar"]