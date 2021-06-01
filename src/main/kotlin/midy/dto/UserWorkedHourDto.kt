package midy.dto


import org.joda.time.*
import java.math.*

data class UserWorkedHourDto(
	val id: Int,
	val date: DateTime,
	val hours: BigDecimal,
)

data class UserWorkedHourDtoString(
	val id: Int,
	val date: String,
	val hours: String,
)
