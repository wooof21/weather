FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/weather-1.0.0.jar /app/weather.jar

EXPOSE 8080

CMD ["java", "-jar", "weather.jar"]

# docker build -t weather .

# create a network to connect to mysql container <network_name>
# docker network create weather_network

#inspect network
#docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' weather_sql_container
#inspect mysql network settings
#docker inspect weather_sql_container --format='{{json .NetworkSettings.Networks}}' | jq

# docker run -p 3306:3306 --name weather_sql_container --network weather_network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_PASSWORD=root -e MYSQL_DATABASE=weather -d mysql


# connect mysql container to network <network_name> & <mysql container name>
# docker network connect mysql_network weather_sql_container

# run the app and provide the env variables
# docker run -p 8080:8080 weather


#docker network ls
#docker network rm <network_name>
#docker network create <network_name>