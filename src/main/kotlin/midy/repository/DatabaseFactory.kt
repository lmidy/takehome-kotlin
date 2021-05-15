package midy.repository

import com.typesafe.config.*
import com.zaxxer.hikari.*
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*
import kotlinx.coroutines.*
import org.flywaydb.core.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import com.zaxxer.hikari.HikariConfig
import midy.model.*
const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"


    fun Application.initDB() {
        val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
        val dbConfig = HikariConfig(configPath)
        val dataSource = HikariDataSource(dbConfig)
        Database.connect(dataSource)
       // createTables()
        //LoggerFactory.getLogger(Application::class.simpleName).info("Initialized takehome Database")
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        val dbUrl = appConfig.property("db.jdbcUrl").getString()
        val dbUser = appConfig.property("db.dbUser").getString()
        val dbPassword = appConfig.property("db.dbPassword").getString()



   val flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPassword).load()
           flyway.migrate()
   }


    private fun createTables() = transaction {
        SchemaUtils.create(
            Users, WorkedHours
        )
    }