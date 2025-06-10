package gb.coding.lightnovel.core.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Instant.formatDate(): String {
    val localDate = this
        .toJavaInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val formatter = DateTimeFormatter
        .ofPattern("d 'de' MMMM, yyyy", Locale("pt", "BR"))

    // Month names to uppercase
    return localDate.format(formatter).replaceFirstChar { it.uppercase() }
}