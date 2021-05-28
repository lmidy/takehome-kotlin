package midy.dto

import org.jetbrains.exposed.sql.jodatime.*
import org.joda.time.*


data class UserWorkedHourDto(
	val id: Int,
	val date: String,
	val hours: String,
	val created_at: DateTime
)
