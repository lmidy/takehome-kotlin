package midy.model


import midy.model.WorkedHours.defaultExpression
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*



/**
 * Code representation of the users table in database DDL
 */
object Users : Table() {
    val id = integer("id").autoIncrement()
    val manager_id = integer("manager_id").nullable()
    val firstname = varchar("first_name", 50)
    val lastname = varchar("last_name", 50)
    val email = varchar("email", 50).uniqueIndex()
    val active = bool("active")
    val created_at = datetime("created_at").defaultExpression(CurrentDateTime())
    override val primaryKey = PrimaryKey(id, name="PK_User_ID")
    }



