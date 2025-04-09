package com.recallit.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recallit.presentation.cards_screen.CardsScreen
import com.recallit.presentation.packs_screen.PacksScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.packs,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screens.packs) {
           PacksScreen(
               onCardClick = { navController.navigate(Screens.cards) },
               bottomNavbar = { BottomNavigationBar(navController, 0) }
           )
        }
        composable(Screens.cards) {
            CardsScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
