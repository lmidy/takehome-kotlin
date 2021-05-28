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
        val id = call.parameters["id"]!!.toInt()
        val uid = userService.getUserId(id)

        val workedhours = userService.getWorkedHours(id)
        if (workedhours.isEmpty() and uid.isEmpty()){
            call.respond(HttpStatusCode.OK,"User not found")
        }
        if(workedhours.isEmpty()){
            call.respond(HttpStatusCode.OK, "No hours for this user")
        }else {
            call.respond(HttpStatusCode.OK, workedhours)
        }
    }
    //TODO: implement valid user id function in service
    //TODO: Service - implement join of passed userid to worked hours table

    post("users/{id}/worked_hours") {
        val userWorkedHourDto= call.receive<WorkedHourDTO>()
       // val workedhour = userService.addWorkedHours(userWorkedHourDto)
        //call.respond(workedhour)
        call.respond(HttpStatusCode.Created, "Added worked hours for: $userWorkedHourDto")

    }


}