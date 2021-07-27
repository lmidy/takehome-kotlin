package midy.routes

import io.ktor.application.* // ktlint-disable no-wildcard-imports
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.request.* // ktlint-disable no-wildcard-imports
import io.ktor.response.* // ktlint-disable no-wildcard-imports
import io.ktor.routing.* // ktlint-disable no-wildcard-imports
import midy.dto.* // ktlint-disable no-wildcard-imports
import midy.service.* // ktlint-disable no-wildcard-imports
import org.joda.time.format.* // ktlint-disable no-wildcard-imports
import org.joda.time.format.DateTimeFormat
import java.time.format.* // ktlint-disable no-wildcard-imports

val userService = UserWorkedHoursService()

fun Route.users() {

        /*
    GET http://localhost:3000/v1/users
    */
    get("users") {
        call.respond(userService.getAllUsers())
    }

        /*
    GET http://localhost:3000/v1/users/1/worked_hours
    */
    get("users/{id}/worked_hours") {
        val id = call.parameters["id"]!!.toInt()
        val uid = userService.getUserId(id)
        val workedhours = userService.getWorkedHours(id)
        if (workedhours.isEmpty() or uid.isEmpty()) {
            call.respond(HttpStatusCode.NotFound, "User or Hours not found")
        } else {
            call.respond(HttpStatusCode.OK, workedhours)
        }
    }

        /*
    POST http://localhost:3000/v1/users/1/worked_hours
   */
    post("users/{id}/worked_hours") {
        val id = call.parameters["id"]!!.toInt()
        val uid = userService.getUserId(id)
        val formatter = DateTimeFormat.forPattern("YYYY-mm-dd")
        val request = call.receive<WorkedHourDTO>()
        val passeddate = request.date
        val dt = formatter.parseDateTime(passeddate)
        val decimalhours = request.hours.toBigDecimal()
        if (uid.isEmpty() or passeddate.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "User or date is invalid")
        } else {
            val userworkedhour = UserWorkedHourDTO(
                id = id,
                date = dt,
                hours = decimalhours,
            )
            val addedresult = userService.addWorkedHours(userworkedhour)
            call.respond(HttpStatusCode.Created, "worked_hour inserted")
        }
    }
}
