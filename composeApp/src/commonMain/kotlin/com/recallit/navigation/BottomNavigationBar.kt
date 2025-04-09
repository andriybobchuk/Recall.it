package com.recallit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedItemIndex: Int) {
    val items = listOf(
        BottomNavigationItem("My Packs", Icons.Filled.Home, Icons.Outlined.Search),
        BottomNavigationItem("Progress", Icons.Filled.Star, Icons.Outlined.Star),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    if (selectedItemIndex != index) {
                        when (index) {
                            0 -> navController.navigate(Screens.packs) { popUpTo(Screens.packs) }
                            1 -> navController.navigate(Screens.cards) { popUpTo(Screens.packs) }
                        }
                    }
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}
