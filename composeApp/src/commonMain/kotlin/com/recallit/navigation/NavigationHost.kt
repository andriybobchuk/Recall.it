package com.recallit.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.packs,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screens.packs) {
           //PacksScreen(navController)
        }
        composable(Screens.cards) {
            //CardsScreen(navController)
        }
    }
}
