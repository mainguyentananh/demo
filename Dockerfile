FROM openjdk:8
WORKDIR /app
COPY . .
RUN mvn clean package
CMD ["java", "-jar", "demo.jar"]