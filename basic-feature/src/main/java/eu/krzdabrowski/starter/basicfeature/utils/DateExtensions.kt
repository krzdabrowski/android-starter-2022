package eu.krzdabrowski.starter.basicfeature.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


const val DATE_DAY_FORMAT: String = "dd MMM yyyy"
fun LocalDate.getDateFormatted(format: String = DATE_DAY_FORMAT): String =
    this.format(DateTimeFormatter.ofPattern(format, Locale.US))

fun String.toLocalDate(format: String = DATE_DAY_FORMAT): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(format, Locale.US))
