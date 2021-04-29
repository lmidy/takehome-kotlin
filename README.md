# takehome-kotlin
This project is an attempt to complete the [SpotHero Engineering Manager Take Home Challenge](https://github.com/spothero/eng-mgr-take-home-challenge)


## Libraries used:
- [Kotlin](https://github.com/JetBrains/kotlin) - Programming Language
- [Ktor](https://ktor.io/docs/creating-http-apis.html) - Kotlin async web framework
- [Netty](https://ktor.io/docs/engines.html) - Async web server
- [Exposed](https://github.com/JetBrains/Exposed/wiki) - Kotlin SQL framework
- [HikariCP](https://github.com/brettwooldridge/HikariCP)- JDBC connection pooling
- Flyway and [Ktor Flyway Feature](https://github.com/viartemev/ktor-flyway-feature) - Database migrations
- Kotlin [Serialization](https://medium.com/@gurpreetsk/getting-started-with-kotlin-serialization-3315c59bafb2) 

### Libraries to add:
- junit
- kotlin logging

## Tools leveraged
- Docker for Postgres
- Kotlin IntelliJ IDEA Plugin
- Browser or Terminal
- Server is configured to start on port 3000 vs 8080

## Getting Started
- Clone the repo.
- Docker Compose Up get the database running
- In the root directory execute ./gradlew run
- By default the server will start on port 3000.


## Routes
`GET http://localhost:3000/ --> I may create a simple HTML page with the routes`
>right now this is returning "HELLO WORLD!"

`GET http://localhost:3000/v1/users --> return all active users`
>right now this is returning "No users found"`

`GET http://localhost:3000/v1/users/{id}/worked_hours --> get all worked_hours for that by id`
>right now this is returning Worked Hours Not Found`

`POST users/{id}/worked_hours --> adds worked hours to the database by providing a JSON object`
>right now this is not working

#### Project Structure
```
+ model/ yes my data classes and objects are together 
  + User - 
  + Worked_hours - 

+ repository/
 + DatabaseFactory - Hikari connection stuff in here
 + UserRepository - all logic here

+ routes
  + ApiRoute - to support API versioning
  + UserRoutes - routes for everything after v1/users/
  
- Application.kt<- main class trying to keep this as clean as possible
```


