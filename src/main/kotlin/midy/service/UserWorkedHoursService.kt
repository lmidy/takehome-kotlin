package midy.service

import midy.dto.*
import midy.model.*
import midy.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.*

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
		WorkedHours.slice(WorkedHours.user_id, WorkedHours.date, WorkedHours.hours).
		select { WorkedHours.user_id eq id}.map {maptoUserWorkedHourDTO(it)}
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
			id = it[WorkedHours.user_id],
			date = it[WorkedHours.date].toString("yyyy-mm-dd"),
			hours = it[WorkedHours.hours]
		)

//	val currentTimestamp = System.currentTimeMillis()
//	fun insert(workedhour: UserWorkedHourDto): UserWorkedHourDto = dbQuery {
//		WorkedHours.insert {
//			it[WorkedHours.date] = date,
//			it[WorkedHours.hours] = hours
//			it[WorkedHours.created_at]= System.Date()
//		}
//	}
}

