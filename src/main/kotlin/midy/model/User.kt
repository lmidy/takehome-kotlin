package midy.model


import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*



/**
 * Code representation of the users table in database DDL, I dropped the creation date
 */
object Users : Table() {
    val id = integer("id").autoIncrement()
    val manager_id = integer("manager_id").nullable()
    val firstname = varchar("firstname", 50)
    val lastname = varchar("lastname", 50)
    val email = varchar("email", 50).uniqueIndex()
    val active = bool("active")
    override val primaryKey = PrimaryKey(id, name="PK_User_ID")
    }

/*class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var manager_id by Users.manager_id
    var firstname by Users.firstname
    var lastname by Users.lastname
    var email by Users.email
    var active by Users.active
}*/

