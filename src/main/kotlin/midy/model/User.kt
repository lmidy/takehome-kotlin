package midy.model


import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*
import kotlinx.serialization.Serializable


object Users : IntIdTable() {
    //val id = integer("id").autoIncrement()
    val manager_id = integer("manager_id").nullable()
    val first_name = varchar("first_name", 50)
    val last_name = varchar("last_name", 50)
    val email = varchar("email", 50)
    val active = bool("active")
    val created_at = datetime("created_at")
    override val primaryKey = PrimaryKey(id, name = "id")
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var manager_id by Users.manager_id
    var first_name by Users.first_name
    var last_name by Users.last_name
    var email by Users.email
    var active by Users.active
    var created_at by Users.created_at

    //fun toUser() = manager_id?.let { User(id.value, it, first_name, last_name, email, active) }
}

@Serializable
data class User(
    val id: EntityID<Int>,
    val manager_id: Int?,
    val firstname: String,
    val lastname: String,
    val email: String,
    val active: Boolean
)

val userStorage = mutableListOf<User>()
