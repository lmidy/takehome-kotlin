package midy.service

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.sql.DataSource

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
        block: suspend () -> T
    ): T = newSuspendedTransaction { block() }
}
