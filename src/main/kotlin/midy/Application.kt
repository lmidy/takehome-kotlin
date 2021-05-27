package midy

import com.viartemev.ktor.flyway.*
import io.ktor.application.*
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import midy.routes.*
import org.jetbrains.exposed.dao.exceptions.*
import io.ktor.http.HttpStatusCode.Companion.NotFound
import midy.service.*
import org.jetbrains.exposed.sql.*
import java.text.*


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }

    }

    install(StatusPages) {
        exception<EntityNotFoundException> {
        call.respond(NotFound)
        }
    }

    val db = DatabaseFactory.create()
    Database.connect(db)
    install(FlywayFeature) {
        dataSource = db
    }


    install(Routing){

        routing {
            apiRoute()
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
        }
    }
}
