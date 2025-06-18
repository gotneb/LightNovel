package gb.coding.lightnovel.core.domain.model

import androidx.compose.ui.graphics.Color

enum class ReaderTheme(
    val label: String,
    val backgroundColor: Color,
    val textColor: Color,
) {
    Light("Light", Color.White, Color.Black),
    Dark("Dark", Color(0xFF302E33), Color.White),
    Serpia("Serpia", Color(0xFFBFB7A0), Color.White),
}