package midy

import io.ktor.application.*
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import midy.routes.*
import org.jetbrains.exposed.dao.exceptions.*
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.serialization.*
import midy.repository.*


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {
        exception<EntityNotFoundException> {
        call.respond(NotFound)
        }
    }

    initDB()
    install(Routing){

        routing {
            apiRoute()
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
        }
    }
}
