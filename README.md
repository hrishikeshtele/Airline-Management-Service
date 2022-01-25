# HT Airline Management Service
* [Introduction](#introduction)
* [Service](#service)
* [Open-API](#Open-API-v3)
* [Prerequisites](#prerequisites)
    * [Development Tools](#development-tools)
* [Build Project](#build-project)
* [Run Project](#run-project)
    * [Environment variables](#environment-variables)
    * [Run Microservice](#run-microservice)

## Introduction
Airline management service that has features like customer registration, mileage rewards, reservations/itineraries for each airline customer, auto generate flights, email notification for defined actions, database encryption to keep personal information safe.

## Service

| Purpose              | **API**                        | **API** | **Description**                                                                                                                            |
|----------------------|--------------------------------|---------|--------------------------------------------------------------------------------------------------------------------------------------------|
| Sign Up              | /customer/v1.0/sign-up         | POST    | API used for registration of customer on airline system.                                                                                   |
| Change Password      | /customer/v1.0/change-password | POST    | Used to set the password of the customer. It accepts the old and new password as input parameters.                                         |
| Forgot Password      | /customer/v1.0/forgot-password | POST    | If the customer forgets the password, this method can be used to reset the password. New password would be sent to the customer via email. |
| Sign In              | /customer/v1.0/sign-in         | POST    | Used to sign-in to airline system.                                                                                                         |
| List Airports        | /airline/v1.0/list-airports    | POST    | Used to list onboarded airports on the system.                                                                                             |
| Get Flight           | /airline/v1.0/flight           | GET     | Used to get flight details.                                                                                                                |
| Create Flight        | /airline/v1.0/flight           | POST    | Used to create flight.                                                                                                                     |
| Search Flight        | /airline/v1.0/flight/search    | POST    | If the consumer forgets the password, this method can be used to reset the password. New password would be sent to the consumer.           |
| Update Flight        | /airline/v1.0/flight           | PUT     | Used to update flight details.                                                                                                             |
| Delete Flight        | /airline/v1.0/flight           | DEL     | Used to delete created flight.                                                                                                             |
| Book Flight          | /airline/v1.0/book             | POST    | Used to book flight.                                                                                                                       |
| Select Seat          | /airline/v1.0/book/seat        | POST    | Used to select seat number for the booking.                                                                                                |
| List Booked Flights  | /airline/v1.0/book/list        | POST    | Used to list booked flight details.                                                                                                        |
| Delete Booked Flight | /airline/v1.0/book             | DEL     | Used to delete booked flight.                                                                                                              |
| Generate Flights     | /airline/v1.0/generate-flights | POST    | Used to generate random flights on system for next week.                                                                                   |

## Open API V3

Request and response model specification are described using Swagger and Open API V3 Documentation - [Link](https://hps-airline.herokuapp.com/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

## Prerequisites

### Development Tools


- [ ] **Open JDK** downstream distribution from Oracle - [Openjdk 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) is used as a JDK.

- [ ] [Springboot-2.5.5](https://spring.io/blog/2021/09/23/spring-boot-2-5-5-available-now) is an open source Java-based framework used to create a micro Service. It is used to build stand-alone and production ready spring applications.

- [ ] **PostgreSQL 14.1** is used as a relational database. Choose your installer type depending on the host operating system [here](https://www.postgresql.org/download/)

- [ ] [Maven 3.6](https://maven.apache.org/download.cgi) is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

## Build Project

- Make sure Postgres Server is up and running with required schema. If not then you can use db_schema.sql file in resources/db/init folder to create database schema on the server.

- Configure SMTP env variables before running app. Read [this](https://www.jotform.com/help/392-how-to-use-your-gmail-account-as-your-email-sender-via-smtp/) for setting a Google account SMTP server.

- Run below maven commands from ***project_root*** directory for various tasks.

* **clean project**: This will clean the **Target** directory (aka output folder).

  ```
  mvnw clean
  ```

* **clean & compile project**: This will clean the **Target** directory and only compile the project

  ```
  mvn clean compile
  ```

* **clean & build project**: This will perform the **clean** task followed by **build** process. The will  clean,build, run test-cases, calculate code coverage and generate the artifact file in the **target** directory.

  ```
  mvnw clean package
  ```

## Run Project

### Environment variables

| Sr.No | Key              | Purpose                                                                                                          | Default Value                             | Mandatory? |
|-------|------------------|------------------------------------------------------------------------------------------------------------------|-------------------------------------------|------------|
| 1.    | DB_URL           | This string should consists of IP Address along with port number of the server over which postgresql is running. | jdbc:postgresql://localhost:5432/postgres | Yes        |
| 2.    | DB_USERNAME      | Username of the postgresql server to be used by microservice for connection                                      | postgres                                  | Yes        |
| 3.    | DB_PASSWORD      | Password of the postgresql user provided as a value for the above key "DB_USERNAME".                             | null                                      | Yes        |
| 4.    | LOGGING_LEVEL    | Setting the Log Level when running the Spring Boot application.                                                  | WARN                                      | No         |
| 5.    | DB_SECRET        | This sets secret value which will be used while encrypting PII fields in database.                               | null                                      | Yes        |
| 6.    | TOKEN_TTL        | The time duration for which the jwt token is valid.                                                              | 1200000                                   | No         |
| 7.    | SMTP_SERVER      | SMTP server url.                                                                                                 | smtp.gmail.com                            | No         |
| 8.    | SMTP_PORT        | SMTP server port.                                                                                                | 587                                       | No         |
| 9.    | SMTP_USERNAME    | SMTP server username.                                                                                            | null                                      | Yes        |
| 10.   | SMTP_PASSWORD    | SMTP server password.                                                                                            | null                                      | Yes        |
| 11.   | SMTP_AUTH        | SMTP server authentication enable true or false.                                                                 | true                                      | No         |
| 12.   | EHLO             | This enables SMTP server EHLO protocol. Value must be true or false.                                             | true                                      | No         |
| 13.   | START_TLS_ENABLE | This enables tls for smtp server. Value must be true or false.                                                   | true                                      | No         |
| 14.   | SMTP_SSL         | This enables ssl for smtp server. Value must be true or false.                                                   | false                                     | No         |
| 15.   | SMTP_DEBUG       | This sets smtp server into debug mode. Value must be true or false.                                              | false                                     | No         |

### Run Microservice

If you need to perform deployment on local system, it can be done by running jar directly or using a Docker image.
Use the below-mentioned command to run the microservice directly using jar file.

```bash
java -jar <app-name>.jar &
```