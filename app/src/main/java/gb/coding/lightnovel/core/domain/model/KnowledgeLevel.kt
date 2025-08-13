package gb.coding.lightnovel.core.domain.model

import androidx.compose.ui.graphics.Color

enum class KnowledgeLevel(val id: Int, val label: String) {
    IGNORE(0, "Ignore"),
    NEW(1, "New"),
    RECOGNIZED(2, "Recognized"),
    FAMILIAR(3, "Familiar"),
    LEARNED(4, "Learned"),
    KNOWN(5, "Known");

    companion object {
        fun fromId(id: Int): KnowledgeLevel =
            entries.find { it.id == id } ?: NEW
    }
}

fun KnowledgeLevel.getBackgroundColor(isDarkTheme: Boolean): Color? = when (this) {
    KnowledgeLevel.IGNORE, KnowledgeLevel.KNOWN -> null
    KnowledgeLevel.NEW -> if (isDarkTheme) Color(0xFF5D4037) else Color(0xFFFFF3E0)
    KnowledgeLevel.RECOGNIZED -> if (isDarkTheme) Color(0xFF0D47A1) else Color(0xFFE3F2FD)
    KnowledgeLevel.FAMILIAR -> if (isDarkTheme) Color(0xFF1B5E20) else Color(0xFFE8F5E9)
    KnowledgeLevel.LEARNED -> if (isDarkTheme) Color(0xFFF57F17) else Color(0xFFFFFDE7)
}

fun KnowledgeLevel.getTextColor(isDarkTheme: Boolean): Color = when (this) {
    KnowledgeLevel.IGNORE, KnowledgeLevel.KNOWN -> Color.Unspecified
    KnowledgeLevel.NEW -> if (isDarkTheme) Color(0xFFFFF3E0) else Color(0xFF5D4037)
    KnowledgeLevel.RECOGNIZED -> if (isDarkTheme) Color(0xFFE3F2FD) else Color(0xFF0D47A1)
    KnowledgeLevel.FAMILIAR -> if (isDarkTheme) Color(0xFFE8F5E9) else Color(0xFF1B5E20)
    KnowledgeLevel.LEARNED -> if (isDarkTheme) Color(0xFFFFFDE7) else Color(0xFFF57F17)
}