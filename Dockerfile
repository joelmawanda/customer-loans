#load the image from either local or docker hub
FROM openjdk:17

#create a directory where the application will be placed
RUN mkdir /app

#copy the compiled jar file to the /app directory
COPY target/demo-0.0.1-SNAPSHOT.jar /app/

#set up default enviroment variables
ENV SERVER_PORT=8080 \
    PROFILE=dev    \
    DB_USER=postgres \
    DB_PASS=test \
    DB_MAX_CONN=3 \
    DB_URL=jdbc:postgresql://postgresdb:5432/customer_loans


#set the current working directory
WORKDIR /app

#start the app
ENTRYPOINT exec java -Dserver.port=${SERVER_PORT} -Dspring.profiles.active=${PROFILE} -Dspring.datasource.username=${DB_USER} -Dspring.datasource.password=${DB_PASS} -Dspring.datasource.url=${DB_URL} -Dspring.datasource.hikari.maximum-pool-size=${DB_MAX_CONN} -jar demo-0.0.1.jar