package midy.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import midy.model.*
import midy.repository.*

val userController = UserRepository()

fun Route.users() {

    //get all active users
    get("users") {
            if (userStorage.isNotEmpty()) {
                call.respond(userController.getAllUsers())
            } else {
                call.respondText("No users found", status = HttpStatusCode.NotFound)
            }
        }

 //GET all worked_hours for users
    get("users/{id}/worked_hours") {
        val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
        val workedhoursentry = workedHourStorage.find { it.user_id == 1 } ?: return@get call.respondText(
            "Worked Hours Not Found",
            status = HttpStatusCode.NotFound
        )
        call.respond(workedhoursentry)
    }


    post ("users/{id}/worked_hours") {
        call.respondText("post for user, ${call.parameters["id"]}")
    }


}