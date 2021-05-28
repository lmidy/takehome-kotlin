package midy.service


import midy.dto.*
import midy.model.*
import midy.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class UserWorkedHoursService {

	/**
	 * Lists all active users
	 */
	suspend fun getAllUsers(): List<UserDTO> = dbQuery {
		Users.slice(Users.id,Users.firstname, Users.lastname, Users.email).
		select { Users.active eq true}.map {maptoUserDTO(it)}
	}

	/**
	 * Lists all worked hours for a particular user
	 */
	suspend fun getWorkedHours(id: Int): List<UserWorkedHourDto> = dbQuery {
		(Users innerJoin WorkedHours)
			.select {
				Users.id.eq(id) and WorkedHours.id.eq(id)
			}
			.map {maptoUserWorkedHourDTO(it)
			}.toList()
	}

	/**
	 * Gets a particular user
	 */
	suspend fun getUserId(id: Int): List<UserDTO> = dbQuery {
		Users.slice(Users.id,Users.firstname, Users.lastname, Users.email).
		select { Users.id eq id}.map {maptoUserDTO(it)}
	}

	suspend fun addWorkedHours(userWorkedHourDto: UserWorkedHourDto ) = dbQuery {
		//val current_time = System.currentTimeMillis()
		WorkedHours.insert {
			it[WorkedHours.id] = id
			it[WorkedHours.date] = date
			it[WorkedHours.hours] = hours
			it[WorkedHours.created_at] = created_at
		}
	}

	fun maptoUserDTO(row: ResultRow): UserDTO =
		UserDTO(
			id = row[Users.id],
			firstname = row[Users.firstname],
			lastname = row[Users.lastname],
			email = row[Users.email],
		)

	fun maptoUserWorkedHourDTO(it: ResultRow): UserWorkedHourDto =
		UserWorkedHourDto(
			id = it[WorkedHours.id],
			date = it[WorkedHours.date].toString(),
			hours = it[WorkedHours.hours].toString(),
			created_at = it[WorkedHours.created_at]
		)


}


