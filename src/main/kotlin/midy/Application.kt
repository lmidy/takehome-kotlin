package midy

import com.sun.xml.internal.ws.developer.*
import io.ktor.application.*
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import midy.repository.*
import midy.routes.*
import org.jetbrains.exposed.dao.exceptions.*
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.serialization.*


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

/**
 * Please note that you can use any other name instead of *module*.
 * Also note that you can have more then one modules in your application.
 * */
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    initDB()

    install(ContentNegotiation) {
        json()
        install(CallLogging)
        install(StatusPages) {
            exception<EntityNotFoundException> {
                call.respond(NotFound)
            }
        }
        install(Routing)

        routing {
            apiRoute()
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

        }
    }
}
