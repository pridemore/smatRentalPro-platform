FROM openjdk:17-alpine

#COPY "./target/microplan-service.jar" .

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -DMYSQL_HOST=${MYSQL_HOST} -DMYSQL_USER=${MYSQL_USER} -DMYSQL_PASSWORD=${MYSQL_PASSWORD} -DMYSQL_PORT=${MYSQL_PORT}", "/opt/smatech/smartrentalpro-backend.jar"]

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "/opt/smatech/smartrentalpro-backend.jar"]