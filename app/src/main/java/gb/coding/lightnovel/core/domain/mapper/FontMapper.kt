package gb.coding.lightnovel.core.domain.mapper

import androidx.compose.ui.text.font.FontFamily
import gb.coding.lightnovel.core.domain.model.ReaderFont
import gb.coding.lightnovel.ui.theme.Lora
import gb.coding.lightnovel.ui.theme.NotoSans
import gb.coding.lightnovel.ui.theme.SourceSerif4

fun ReaderFont.toFontFamily(): FontFamily = when (this) {
    ReaderFont.SourceSerif4 -> SourceSerif4
    ReaderFont.Lora -> Lora
    ReaderFont.NotoSans -> NotoSans
}