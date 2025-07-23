CREATE TABLE IF NOT EXISTS weather_city_data (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
city VARCHAR(255),
country VARCHAR(255),
temperature DOUBLE,
weather_description VARCHAR(255),
aqi INT,
air_quality_status VARCHAR(255),
weather_timestamp INTEGER,
timezone VARCHAR(255));

CREATE TABLE IF NOT EXISTS auth_users (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(255),
password VARCHAR(255)
);

INSERT INTO auth_users VALUES (null, "admin", "{noop}admin");
--
--INSERT INTO auth_users VALUES (null, "testuser1", "{noop}testuser1");
--
--INSERT INTO auth_users VALUES (null, "testuser2", "{noop}testuser2");