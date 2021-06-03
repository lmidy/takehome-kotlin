package midy.dto

import org.joda.time.DateTime
import java.math.BigDecimal

data class UserWorkedHourDTO(
    val id: Int,
    val date: DateTime,
    val hours: BigDecimal
)
