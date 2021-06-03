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
import midy.model.*
import midy.service.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused")
@kotlin.jvm.JvmOverloads
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
    }

    val db = DatabaseFactory.create()
    Database.connect(db)

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users, WorkedHours)
    }

    install(FlywayFeature) {
        dataSource = db
        locations = arrayOf("resources/db/migration")
        commands(Clean, Baseline, Migrate)
        schemas= arrayOf("V1.0_DataLoad.sql")
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
