package midy.model

import kotlinx.serialization.Serializable
import midy.model.WorkedHours.date
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.*
import java.sql.*

object WorkedHours : IntIdTable() {
    val user_id = reference("userid", Users)
    val date = date("date")
    val hours = varchar("first_name", 50)
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(user_id, name = "user_id")
}

class WorkedHoursEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkedHoursEntity>(WorkedHours)
 //todo add val and also add fk references
}

@Serializable
data class WorkedHour( //validate this whole section
    val user_id: Int,
    val date: java.sql.Date,
    val hours: String,
    val created_at: Timestamp
)
val workedHourStorage = mutableListOf<WorkedHour>()