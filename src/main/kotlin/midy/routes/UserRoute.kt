package midy.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import midy.dto.*
import midy.model.*
import midy.service.*
import org.jetbrains.exposed.sql.*
import java.util.*

val userService = UserWorkedHoursService()

fun Route.users() {


    get("users") {
            call.respond(userService.getAllUsers())
        }

    get("users/{id}/worked_hours") {
//        val id= call.parameters["id"].toInt()
//        val userDTO = call.receive<UserWorkedHourDto>()
//        userService.getWorkedHours(id)
        call.respond(HttpStatusCode.OK)
        call.respond(HttpStatusCode.OK, "We recieved this id: $")
    }

    post("users/{three}/worked_hours") {
        val userWorkedHourDto= call.receive<UserWorkedHourDto>()
        //userService.insert(UserWorkedHourDto)
        call.respond(HttpStatusCode.Created)

    }


}