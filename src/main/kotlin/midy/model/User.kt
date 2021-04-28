package midy.model


import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*
import kotlinx.serialization.Serializable

data class UserDTO(
        val id: EntityID<Int>,
        val manager_id: Int?,
        val firstname: String,
        val lastname: String,
        val email: String,
        val active: Boolean
)
object Users : IntIdTable() {
    val manager_id = integer("manager_id").nullable()
    val firstname = varchar("firstname", 50)
    val lastname = varchar("lastname", 50)
    val email = varchar("email", 50).uniqueIndex()
    val active = bool("active")
    val created_at = date("created_at")
    override val primaryKey = PrimaryKey(id, name="PK_User_ID")
    override fun toString(): String = "User($id, $firstname, $lastname, $email, $active)"
    }

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var manager_id by Users.manager_id
    var firstname by Users.firstname
    var lastname by Users.lastname
    var email by Users.email
    var active by Users.active
    var created_at by Users.created_at
}

val userStorage = mutableListOf<UserDTO>()
