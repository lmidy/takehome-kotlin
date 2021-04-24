package midy.repository

import midy.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

/*interface IUserHoursRepository {
    //fun addUser()
    fun getAll(): ArrayList<User>
    //fun addWorkedHour()
   // fun getAllWorkedHours()*/


class UserController {

    fun getAllUsers(): ArrayList<User> {
        val users: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                users.add(
                    User(
                        id = it[Users.id],
                        manager_id = it[Users.manager_id],
                        firstname = it[Users.first_name],
                        lastname = it[Users.last_name],
                        email = it[Users.email],
                        active = it[Users.active]
                    )
                )
            }
        }
        return users
    }
}
