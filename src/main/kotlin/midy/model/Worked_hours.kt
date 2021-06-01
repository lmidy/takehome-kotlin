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
    val id = integer("id").references(Users.id)
    val date = date("date")
    val hours = decimal("hours", scale= 2, precision = 4)
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime())
    override val primaryKey = PrimaryKey(id, name = "worked_hours_pkey")

}


