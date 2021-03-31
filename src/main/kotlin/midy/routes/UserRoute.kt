package midy.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.users() {

    //get all active users
    get("users") {
        call.respondText("get users")
    }
 //GET all worked_hours for users
    get("users/{id}/worked_hours") {
        call.respondText("Get worked hours")
    }

    post ("users/{id}/worked_hours") {
        call.respondText("post for user, ${call.parameters["id"]}")
    }


}