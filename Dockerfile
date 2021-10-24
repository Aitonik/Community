#FROM maven:3.6.3-openjdk-14-slim AS MAVEN_BUILD
#ENV JAVA_OPTS="-Xms256m -Xmx2048m"
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#RUN mvn package
#FROM openjdk:14-jdk-slim
#ENV JAVA_OPTS="-Xms256m -Xmx2048m"
#WORKDIR /app
#COPY --from=MAVEN_BUILD /build/target/fav-list.jar /app/
#EXPOSE 8099
#ENTRYPOINT ["java", "-jar", "/app/fav-list.jar"]


# back
FROM java:8-jdk-alpine
ENV JAVA_OPTS="-Xms256m -Xmx2048m"
EXPOSE 8099
ADD target/Community.jar Community.jar
ENTRYPOINT ["java","-jar","Community.jar"]
#
## For Java 8, try this
#FROM openjdk:8-jdk-alpine
#
## Refer to Maven build -> finalName
#ARG JAR_FILE=target/fav-list.jar
#
## cd /opt/app
#WORKDIR /opt/app
#
## cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar
#
## java -jar /opt/app/app.jar
#ENTRYPOINT ["java","-jar","app.jar"]


##### Stage 1: Build the application
#FROM openjdk:8-jdk-slim as build
#
## Set the current working directory inside the image
#WORKDIR /app
#
## Copy maven executable to the image
#COPY mvnw .
#COPY .mvn .mvn
#
## Copy the pom.xml file
#COPY pom.xml .
#
## Build all the dependencies in preparation to go offline.
## This is a separate step so the dependencies will be cached unless
## the pom.xml file has changed.
#RUN ./mvnw dependency:go-offline -B
#
## Copy the project source
#COPY src src
#
## Package the application
#RUN ./mvnw package -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#
##### Stage 2: A minimal docker image with command to run the app
#FROM openjdk:8-jdk-slim
#
#ARG DEPENDENCY=/app/target/dependency
#
## Copy project dependencies from the build stage
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.fav-list.FavListApplication"]