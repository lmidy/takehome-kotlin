package midy.repository

import midy.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import midy.model.UserDTO
/*interface IUserHoursRepository {
    //fun addUser()
    fun getAll(): ArrayList<User>
    //fun addWorkedHour()
   // fun getAllWorkedHours()*/


class UserRepository {

    suspend fun getAllUsers(): Iterable<UserDTO> = transaction {
               UserEntity.all().map(UserEntity::toUser)
        }
}

