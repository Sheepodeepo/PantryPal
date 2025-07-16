#Build app/jar file
FROM gradle:8.14.3-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

#Execute/Run
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/build/libs/PantryPal-0.0.1-SNAPSHOT.jar ./PantryPal-aws.jar
EXPOSE 8080
CMD ["java", "-jar", "PantryPal-aws.jar", "--spring.profiles.active=prod"]