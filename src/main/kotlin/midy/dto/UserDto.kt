package midy.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
	val id: Int,
	val firstname: String,
	val lastname: String,
	val email: String,
)