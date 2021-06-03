package midy.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*
import java.sql.*
import java.sql.Date

/**
 * Code representation of the Worked Hours table database DDL
 */
object WorkedHours : Table("worked_hours") {
    val user_id = integer("user_id").references(Users.id)
    val date = date("date")
    val hours = decimal("hours", scale= 2, precision = 4)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime())
    override val primaryKey = PrimaryKey(user_id, name = "worked_hours_pkey")
    init {
        index(true, user_id, date) // composite unique constraint only 1 entry per day per user
    }

}


