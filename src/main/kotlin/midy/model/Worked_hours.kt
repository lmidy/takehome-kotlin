package midy.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.sql.jodatime.*

import java.sql.*

object Worked_hours : IntIdTable() {
    val user_id = reference("id", Users)
    val date = date("date")
    val hours = varchar("first_name", 50)
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(user_id, name = "user_id")
}

class WorkedHoursEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkedHoursEntity>(Worked_hours)
 //todo add vals and also add fk references
}


data class Worked_hour( //validate this whole section
    val user_id: Int,
    val date: java.sql.Date,
    val hours: String,
    val created_at: Timestamp
)