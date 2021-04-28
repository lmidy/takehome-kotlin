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
        createTables()
        //LoggerFactory.getLogger(Application::class.simpleName).info("Initialized takehome Database")
    }

    private fun createTables() = transaction {
        SchemaUtils.create(
            Users, WorkedHours
        )
    }