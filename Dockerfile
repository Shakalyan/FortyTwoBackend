FROM amazoncorretto:17-alpine

COPY . ./app
WORKDIR /app
RUN ./gradlew build

ENTRYPOINT ["java", "-jar", "./build/libs/FortyTwoBackend-0.0.1-SNAPSHOT.jar"]
