# takehome-kotlin
This project is an attempt to complete the [SpotHero Engineering Manager Take Home Challenge](https://github.com/spothero/eng-mgr-take-home-challenge)

[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![language](https://img.shields.io/badge/language-Kotlin-blue)](https://kotlinlang.org/)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.32-blue.svg?logo=kotlin)](http://kotlinlang.org)

## Libraries used:
- [Kotlin](https://github.com/JetBrains/kotlin) - Programming Language
- [Ktor](https://ktor.io/docs/creating-http-apis.html) - Kotlin async web framework
- [Netty](https://ktor.io/docs/engines.html) - Async web server
- [Exposed](https://github.com/JetBrains/Exposed/wiki) - Kotlin SQL framework
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - JDBC connection pooling
- [Flyway](https://flywaydb.org/) - DB Migration - Database creation and Data Load
## Ktor Features  
- [gson](https://ktor.io/docs/gson.html) - Serialization
- [call logging](https://ktor.io/docs/logging.html#call_logging) - Log incoming client requests
- [status pages](https://ktor.io/docs/gson.html) - Simple interception patterns for calls that result in exceptions
- [routing](https://ktor.io/docs/routing-in-ktor.html) - Core ktor plugin, used to define multiple route handlers


## Tools leveraged
- Docker for Postgres
- Kotlin IntelliJ IDEA Plugin
- Browser or Terminal
- Server configured to start on port 3000 vs 8080

## Getting Started
- Clone the repo `https://github.com/lmidy/takehome-kotlin.git`
- `docker compose up` to get the database running
- In the root directory execute `./gradlew run`
- By default, the server will start on port 3000, with data migrations running with flyway and storing loading data in docker postgres
- If you hit this [http://localhost:3000/](http://localhost:3000/) and get a 'Hello World' means the app is running successfully


## Routes

1. get all active users

`curl http://localhost:3000/v1/users` --> Gets all active users, returns 10 users
```json
[
    {
    "id": 1,
    "firstname": "Rachelle",
    "lastname": "Haggerty",
    "email": "RachelleTHaggerty@rhyta.com"
    },
    {
    "id": 2,
    "firstname": "Daniel",
    "lastname": "Powell",
    "email": "dpowell@yahoo.com"
    },
```
2. get all worked_hours for users --> Gets all worked hours for , returns 6 entries for user 1 

`curl http://localhost:3000/v1/users/1/worked_hours`
```json
[
  {
    "id": 1,
    "date": "2021-01-01",
    "hours": "3.90"
  },
  {
    "id": 1,
    "date": "2021-01-04",
    "hours": "4.13"
  },

```
3. post a new worked_hour record --> new worked hour entered in db for user 1
```
curl -d '{"date": "2021-01-11","hours":5.24}' -H "Content-Type: application/json" -X POST http://localhost:3000/v1/users/1/worked_hours
``` 


#### Project Structure
```
+ model/ Request and response objects are here
  + UserDTO
  + UserWorkedHourDTO
  + UserWorkedHourDTOResponse
  + WorkedHourDTO
  
+ model/ DDL definitions for tables
  + Users 
  + WorkedHours 
  
+ routes
  + ApiRoute - to support API versioning
  + UserRoute - routes for everything after v1/users/

+ service/
 + DatabaseFactory - Hikari DB connection stuff in here
 + UserWorkedHoursService - all logic located here
  
- Application.kt - main class 
```
#### Script
```
takehome_verification.sh has curl requests for positive and several negative test cases
``` 
### Debt / Future Enhancements
1. Add [Valiktor](https://github.com/valiktor/valiktor) for validation
2. Add MockK and jacoco to project [Sample](https://github.com/Kotlin/kotlin-fullstack-sample/pull/28/files#diff-eade18fbfd0abfb6338dbfa647b3215dR17)
3. Add [detekt](https://github.com/detekt/detekt)
4. Enhance dockerfile to run full API in Docker
5. Adding location ktor feature
6. Clean up userService, improve [exception configuration](https://ktor.io/docs/status-pages.html) 
7. Do performance testing with [k6](https://k6.io/docs/using-k6/scenarios/) 
8. Add [swagger-codegen](https://github.com/swagger-api/swagger-codegen)

## Known Defects
2. ERROR: duplicate key value violates unique constraint "worked_hours_pkey" creates false positive
3. ERROR: Entering more than 40 hours creates false positive

