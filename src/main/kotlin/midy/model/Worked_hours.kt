package midy.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*
import java.sql.*

object WorkedHours : Table("worked_hours") {
    val user_id = reference("user_id", Users)
    val date = date("date")
    val hours = varchar("hours", 50)
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(user_id, name = "worked_hours_pkey")
}

@Serializable
data class WorkedHour( //validate this whole section
    val user_id: Int,
    val date: java.sql.Date,
    val hours: String,
    val created_at: Timestamp
)
val workedHourStorage = mutableListOf<WorkedHour>()