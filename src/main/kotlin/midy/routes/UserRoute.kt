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
import org.joda.time.*
import org.joda.time.LocalDateTime.now
import java.sql.DriverManager.println
import java.time.LocalDate.now
import java.time.LocalDateTime
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

    post("users/{id}/worked_hours") {
        val id = call.parameters["id"]!!.toInt()
        val current_time = DateTime()
        val request= call.receive<WorkedHourDTO>()
        val userworkedhour = UserWorkedHourDto(
            id = id,
            date = request.date,
            hours = request.hours,
            created_at = current_time
        )

        println(userworkedhour)
        val addedresult = userService.addWorkedHours(userworkedhour)
        //call.respond(workedhour)
        call.respond(HttpStatusCode.Created, "Added worked hours for: $addedresult")

    }


}