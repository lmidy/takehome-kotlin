package midy.repository

import com.zaxxer.hikari.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import io.ktor.application.Application
import midy.model.*
import org.slf4j.*


const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"


    fun Application.initDB() {
        val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
        val dbConfig = HikariConfig(configPath)
        val dataSource = HikariDataSource(dbConfig)
        Database.connect(dataSource)
        createTables()
        LoggerFactory.getLogger(Application::class.simpleName).info("Initialized takehome Database")
    }

    private fun createTables() = transaction {
        SchemaUtils.create(
            Users, WorkedHours
        )
    }
