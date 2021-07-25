@file:Suppress("UNUSED_PARAMETER", "Reformat")

package midy

import io.ktor.application.* // ktlint-disable no-wildcard-imports
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.response.* // ktlint-disable no-wildcard-imports
import io.ktor.routing.* // ktlint-disable no-wildcard-imports
import midy.routes.apiRoute
import midy.service.DatabaseFactory
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.dao.exceptions.* // ktlint-disable no-wildcard-imports
import org.jetbrains.exposed.exceptions.*
import org.jetbrains.exposed.sql.* // ktlint-disable no-wildcard-imports

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat("yyyy-MM-dd")
            setPrettyPrinting()
        }
    }
    install(StatusPages) {
        exception<EntityNotFoundException> {
            call.respond(NotFound)
        }
        exception<ExposedSQLException> {
            call.respond("Post failed, duplicate time entry")
        }
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
    val db = DatabaseFactory.create()
    Database.connect(db)
    val flyway = Flyway.configure().dataSource(db).load()
    flyway.migrate()

    install(Routing) {
        routing {
            apiRoute()
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
        }
    }
}
