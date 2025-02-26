package com.example.tp_10_navigation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    modifier : Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeRoute(
            onButtonClick = navController::navigateToSecondScreen
//            { secondRoute ->
//                navController.navigateToSecondScreen(secondRoute)
//            }
        )

        secondRoute(
            onSettingsClick = navController::navigateToSettings
        )

        settings()
    }
}