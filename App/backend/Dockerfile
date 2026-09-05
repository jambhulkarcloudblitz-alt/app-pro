FROM maven:3.8.5-openjdk-17


WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
EXPOSE 8080
WORKDIR /app/target

CMD ["java", "-jar","student-registration-backend-0.0.1-SNAPSHOT.jar"]

