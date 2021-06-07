package midy.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.math.BigDecimal

/**
 * Code representation of the Worked Hours table database DDL
 */
object WorkedHours : Table("worked_hours") {
    val user_id: Column<Int> = integer("user_id").references(Users.id)
    val date: Column<DateTime> = date("date")
    val hours: Column<BigDecimal> = decimal("hours", scale = 2, precision = 4)
    val created_at: Column<DateTime> = datetime("created_at").defaultExpression(CurrentDateTime())
    override val primaryKey: PrimaryKey = PrimaryKey(user_id, name = "worked_hours_pkey")

    init {
        index(true, user_id, date) // composite unique constraint only 1 entry per day per user }
    }
}
