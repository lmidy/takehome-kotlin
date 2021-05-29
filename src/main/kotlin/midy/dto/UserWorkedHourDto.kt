package midy.dto


import org.joda.time.*

data class UserWorkedHourDto(
	val id: Int,
	val date: DateTime,
	val hours: String,
)

data class UserWorkedHourDtoString(
	val id: Int,
	val date: String,
	val hours: String,
)
