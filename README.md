Authentication
========================

This demonstrates how to use a Quarkus application for user authentication.

## Start the database

You need a database to store the user identities/roles. Here, we are using [PostgreSQL](https://www.postgresql.org).
To ease the setup, we have provided a `docker-compose.yml` file which start a PostgreSQL container, bind the network ports
and finally creates the users and their credentials by importing the `import.sql` file.

The database can be started using:
 ```bash
 docker-compose up
 ```  

Once the database is up you can start your Quarkus application.

## Start the application

The application can be started using: 

```bash
mvn quarkus:dev
```  

## Test the application

### From  the CLI
The application exposes 3 endpoints:
* `/api/public`
* `/api/admin`
* `/api/users/me`

You can try these endpoints with an http client (`curl`, `HTTPie`, etc).
Here you have some examples to check the security configuration:

```bash
curl -i -X GET http://localhost:8080/api/public  # 'public'
curl -i -X GET http://localhost:8080/api/admin  # unauthorized
curl -i -X GET -u admin:admin http://localhost:8080/api/admin # 'admin'
curl -i -X GET http://localhost:8080/api/users/me # 'unauthorized'
curl -i -X GET -u user:user http://localhost:8080/api/users/me # 'user'
```

_NOTE:_ Stop the database using: `docker-compose down; docker-compose rm`

### Integration testing

We have provided integration tests based on [TestContainers](https://www.testcontainers.org) to verify the security configuration in a JVM and native  mode.

The test can be executed using: 

```bash
# JVM mode
mvn test

# Native mode
mvn verify -Pnative
```  
