package midy.service

import midy.dto.UserDTO
import midy.dto.UserWorkedHourDTO
import midy.dto.UserWorkedHoursDTOResponse
import midy.model.Users
import midy.model.WorkedHours
import midy.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserWorkedHoursService {

	/**
	 * Lists all active users
	 */
	suspend fun getAllUsers(): List<UserDTO> = dbQuery {
		Users.slice(Users.id, Users.firstname, Users.lastname, Users.email)
			.select { Users.active eq true }.map { maptoUserDTO(it) }
	}

	/**
	 * Lists all worked hours for a particular user
	 */
	suspend fun getWorkedHours(id: Int): List<UserWorkedHoursDTOResponse> = dbQuery {
		(Users innerJoin WorkedHours)
			.select {
				Users.id.eq(id) and WorkedHours.user_id.eq(id)
			}
			.map {
				maptoUserWorkedHourDTOString(it)
			}.toList()
	}

	/**
	 * Gets a particular user
	 */
	suspend fun getUserId(id: Int): List<UserDTO> = dbQuery {
		Users.slice(Users.id, Users.firstname, Users.lastname, Users.email)
			.select { Users.id eq id }.map { maptoUserDTO(it) }
	}

	suspend fun addWorkedHours(userWorkedHourDto: UserWorkedHourDTO): InsertStatement<Number> = dbQuery {
		WorkedHours.insert {
			it[user_id] = userWorkedHourDto.id
			it[date] = userWorkedHourDto.date
			it[hours] = userWorkedHourDto.hours
		}
	}

	private fun maptoUserDTO(row: ResultRow): UserDTO =
		UserDTO(
			id = row[Users.id],
			firstname = row[Users.firstname],
			lastname = row[Users.lastname],
			email = row[Users.email],
		)

	fun maptoUserWorkedHourDTO(it: ResultRow): UserWorkedHourDTO =
		UserWorkedHourDTO(
			id = it[WorkedHours.user_id],
			date = it[WorkedHours.date],
			hours = it[WorkedHours.hours],
		)

	private fun maptoUserWorkedHourDTOString(it: ResultRow): UserWorkedHoursDTOResponse =
		UserWorkedHoursDTOResponse(
			id = it[WorkedHours.user_id],
			date = it[WorkedHours.date].toString("yyyy-MM-dd"),
			hours = it[WorkedHours.hours].toString(),
		)
}
