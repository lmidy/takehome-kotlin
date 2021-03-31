package midy.routes

import io.ktor.routing.*

fun Routing.apiRoute() {
    route("/v1") {
        users()
    }
}