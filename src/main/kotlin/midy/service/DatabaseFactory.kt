package midy.service

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
import midy.dto.*
import midy.model.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import org.slf4j.*
import javax.sql.*


    object DatabaseFactory {

        private val appConfig = HoconApplicationConfig(ConfigFactory.load())
        private val dbUrl = appConfig.property("db.jdbcUrl").getString()
        private val dbDriver = appConfig.property("db.dbDriver").getString()
        private val dbUser = appConfig.property("db.dbUser").getString()
        private val dbPassword = appConfig.property("db.dbPassword").getString()

        fun create(): DataSource {
            val config = HikariConfig()
            config.driverClassName = dbDriver
            config.jdbcUrl = dbUrl
            config.username = dbUser
            config.password = dbPassword
            config.maximumPoolSize = 3
            config.isAutoCommit = false
            config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            config.validate()
            return HikariDataSource(config)
        }


        suspend fun <T> dbQuery(
            block: suspend () -> T): T = newSuspendedTransaction { block() }
    }

