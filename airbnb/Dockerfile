# Start with a base image containing Java runtime
FROM openjdk:12-jdk-alpine

# Add Maintainer Info
MAINTAINER Aymeric Vanderkelen <aymeric62vanderkelen@gmail.com>

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/airbnb-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} airbnb.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/airbnb.jar"]

