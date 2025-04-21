package com.recallit.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
               onPackClick = { packId ->
                   navController.navigate("${Screens.cards}/${packId}") }
           )
        }
        composable(
            route = "${Screens.cards}/{packId}",
            arguments = listOf(navArgument("packId") { type = NavType.IntType })
        ) {
            val packId = it.arguments?.getInt("packId") ?: run {
                return@composable
            }
            CardsScreen(
                packId = packId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
