package midy.routes

import io.ktor.routing.*

//todo - refactor to use typesafe routing

fun Routing.apiRoute() {
    route("/v1") {
        users()
    }
}