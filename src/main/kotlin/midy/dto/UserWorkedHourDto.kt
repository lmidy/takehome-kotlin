package midy.dto

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.joda.time.*

@Serializable
data class UserWorkedHourDto(
	val id: Int,
	val date: String,
	val hours: String
)
