package gb.coding.lightnovel.core.navigation

import androidx.annotation.DrawableRes
import gb.coding.lightnovel.R

data class NavItem(
    val label: String,
    @DrawableRes val iconFilledResId: Int,
    @DrawableRes val iconResId: Int,
    val route: Route
)

val bottomNavItems = listOf(
    NavItem(
        label = "Biblioteca",
        iconFilledResId = R.drawable.book_filled,
        iconResId = R.drawable.book,
        route = Route.Library,
    ),
    NavItem(
        label = "Pesquisar",
        iconFilledResId = R.drawable.explore_filled,
        iconResId = R.drawable.explore,
        route = Route.Browse,
    ),
)