package midy.routes

import io.ktor.application.* // ktlint-disable no-wildcard-imports
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.request.* // ktlint-disable no-wildcard-imports
import io.ktor.response.* // ktlint-disable no-wildcard-imports
import io.ktor.routing.* // ktlint-disable no-wildcard-imports
import midy.dto.UserWorkedHourDTO
import midy.dto.WorkedHourDTO
import midy.service.UserWorkedHoursService
import org.joda.time.format.* // ktlint-disable no-wildcard-imports
import org.joda.time.format.DateTimeFormat
import java.time.format.* // ktlint-disable no-wildcard-imports

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
        val userworkedhour = UserWorkedHourDTO(
            id = id,
            date = dt,
            hours = decimalhours,
        )
        val addedresult = userService.addWorkedHours(userworkedhour)
        call.respond(HttpStatusCode.Created, addedresult)
    }
}
