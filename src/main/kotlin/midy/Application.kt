package midy

import com.viartemev.ktor.flyway.* // ktlint-disable no-wildcard-imports
import io.ktor.application.* // ktlint-disable no-wildcard-imports
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.response.* // ktlint-disable no-wildcard-imports
import io.ktor.routing.* // ktlint-disable no-wildcard-imports
import midy.model.Users
import midy.model.WorkedHours
import midy.routes.apiRoute
import midy.service.DatabaseFactory
import org.jetbrains.exposed.dao.exceptions.* // ktlint-disable no-wildcard-imports
import org.jetbrains.exposed.sql.* // ktlint-disable no-wildcard-imports
import org.jetbrains.exposed.sql.transactions.* // ktlint-disable no-wildcard-imports

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
        schemas = arrayOf("V1.0_DataLoad.sql")
    }
    install(Routing) {

        routing {
            apiRoute()
            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
        }
    }
}
