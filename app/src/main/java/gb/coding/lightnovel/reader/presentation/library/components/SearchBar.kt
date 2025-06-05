package gb.coding.lightnovel.reader.presentation.library.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gb.coding.lightnovel.R
import gb.coding.lightnovel.ui.theme.LightNovelTheme

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        placeholder = placeholder,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = null,
                    )
                }
            }
        },
        shape = RoundedCornerShape(100),
        colors = TextFieldDefaults.colors(
            // I can edit background here...
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun SearchBarPreview() {
    LightNovelTheme {
        SearchBar(
            text = "Search",
            onTextChange = {},
        )
    }
}