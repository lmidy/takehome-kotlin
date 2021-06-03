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
import org.joda.time.format.*
import org.joda.time.format.DateTimeFormat
import java.time.format.*
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
        if (workedhours.isEmpty() and uid.isEmpty()) {
            call.respond(HttpStatusCode.OK, "User not found")
        }
        if (workedhours.isEmpty()) {
            call.respond(HttpStatusCode.OK, "No hours for this user")
        } else {
            call.respond(HttpStatusCode.OK, workedhours)
        }
    }

    post("users/{id}/worked_hours") {
        val id = call.parameters["id"]!!.toInt()
        val formatter = DateTimeFormat.forPattern("YYYY-mm-dd")
        val request = call.receive<WorkedHourDTO>()
        val passeddate = request.date
        val dt = formatter.parseDateTime(passeddate)
        val decimalhours = request.hours.toBigDecimal()
        val userworkedhour = UserWorkedHourDto(
            id = id,
            date = dt,
            hours = decimalhours,
        )
        val addedresult = userService.addWorkedHours(userworkedhour)
        call.respond(HttpStatusCode.Created, addedresult)
    }
}
