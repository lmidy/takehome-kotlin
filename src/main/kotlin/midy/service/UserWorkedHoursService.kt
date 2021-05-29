package midy.service


import midy.dto.*
import midy.model.*
import midy.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import java.time.format.*
import java.util.*

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
	suspend fun getWorkedHours(id: Int): List<UserWorkedHourDtoString> = dbQuery {
		(Users innerJoin WorkedHours)
			.select {
				Users.id.eq(id) and WorkedHours.id.eq(id)
			}
			.map {maptoUserWorkedHourDTOString(it)
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
		WorkedHours.insert {
			it[id] = userWorkedHourDto.id
			it[date] = userWorkedHourDto.date
			it[hours] = userWorkedHourDto.hours.toBigDecimal()
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
			date = it[WorkedHours.date],
			hours = it[WorkedHours.hours].toString(),
		)

	fun maptoUserWorkedHourDTOString(it: ResultRow): UserWorkedHourDtoString =
		UserWorkedHourDtoString(
			id = it[WorkedHours.id],
			date = it[WorkedHours.date].toString("YYYY-MM-DD"),
			hours = it[WorkedHours.hours].toString(),
		)


}


