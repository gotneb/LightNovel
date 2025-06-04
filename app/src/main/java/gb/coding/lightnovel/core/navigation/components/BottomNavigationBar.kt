package gb.coding.lightnovel.core.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import gb.coding.lightnovel.core.navigation.NavItem

@Composable
fun BottomNavigationBar(
    items: List<NavItem>,
    selectedNavigationIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex == index,
                label = { Text(item.label) },
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label,
                    )
                },
            )
        }
    }
}