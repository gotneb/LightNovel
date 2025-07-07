package gb.coding.lightnovel.core.domain.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class KnowledgeLevel(val id: Int, val label: String) {
    IGNORE(1, "Ignore"),
    NEW(2, "New"),
    RECOGNIZED(3, "Recognized"),
    FAMILIAR(4, "Familiar"),
    LEARNED(5, "Learned"),
    KNOWN(6, "Known");

    companion object {
        fun fromId(id: Int): KnowledgeLevel =
            entries.find { it.id == id } ?: NEW
    }
}

@Composable
fun KnowledgeLevel.getBackgroundColor(): Color? = when (this) {
    KnowledgeLevel.IGNORE, KnowledgeLevel.KNOWN -> null
    KnowledgeLevel.NEW -> if (isSystemInDarkTheme()) Color(0xFF5D4037) else Color(0xFFFFF3E0)
    KnowledgeLevel.RECOGNIZED -> if (isSystemInDarkTheme()) Color(0xFF0D47A1) else Color(0xFFE3F2FD)
    KnowledgeLevel.FAMILIAR -> if (isSystemInDarkTheme()) Color(0xFF1B5E20) else Color(0xFFE8F5E9)
    KnowledgeLevel.LEARNED -> if (isSystemInDarkTheme()) Color(0xFFF57F17) else Color(0xFFFFFDE7)
}

@Composable
fun KnowledgeLevel.getTextColor(): Color = when (this) {
    KnowledgeLevel.IGNORE, KnowledgeLevel.KNOWN -> Color.Unspecified
    KnowledgeLevel.NEW -> if (isSystemInDarkTheme()) Color(0xFFFFF3E0) else Color(0xFF5D4037)
    KnowledgeLevel.RECOGNIZED -> if (isSystemInDarkTheme()) Color(0xFFE3F2FD) else Color(0xFF0D47A1)
    KnowledgeLevel.FAMILIAR -> if (isSystemInDarkTheme()) Color(0xFFE8F5E9) else Color(0xFF1B5E20)
    KnowledgeLevel.LEARNED -> if (isSystemInDarkTheme()) Color(0xFFFFFDE7) else Color(0xFFF57F17)
}