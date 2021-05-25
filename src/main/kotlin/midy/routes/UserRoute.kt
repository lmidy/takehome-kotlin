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
        val id= call.parameters["id"]!!.toIntOrNull()
        if(id == null){
            call.respond(HttpStatusCode.BadRequest, "id parameter must be a number")
            return@get
        }
        val user_id = userService.getUserId(id)
        if (user_id == null){
            call.respond(HttpStatusCode.NotFound,"user_id not found")
        } else {
            call.respond(user_id)
        }
    }
    //TODO: implement valid user id function in service
    //TODO: Service - implement join of passed userid to worked hours table

    post("users/{id}/worked_hours") {
        val userWorkedHourDto= call.receive<WorkedHourDTO>()
       // val workedhour = userService.addWorkedHours(userWorkedHourDto)
        //call.respond(workedhour)
        call.respond(userWorkedHourDto)

    }


}