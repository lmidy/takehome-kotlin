package midy.routes

import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.apiRoute() {
    route("/v1") {
        users()
    }
}
