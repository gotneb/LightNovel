package gb.coding.lightnovel.core.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import gb.coding.lightnovel.R

data class NavItem(
    val label: String,
    @DrawableRes val iconResId: Int,
    val route: Route
)

val bottomNavItems = listOf(
    NavItem(
        label = "Biblioteca",
        iconResId = R.drawable.book,
        route = Route.Library
    ),
    NavItem(
        label = "Pesquisar",
        iconResId = R.drawable.explore,
        route = Route.Browse
    ),
)