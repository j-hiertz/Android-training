package com.example.tp_10_navigation.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.example.tp_10_navigation.ui.screens.HomeScreen
import com.example.tp_10_navigation.ui.screens.SecondScreen
import com.example.tp_10_navigation.ui.screens.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute // route for home screen

fun NavGraphBuilder.homeRoute(
    onButtonClick: (SecondRoute) -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(onButtonClick = onButtonClick)
    }
}

@Serializable
data class SecondRoute(val name: String = "Toto", val age: Int? = null) // route for second screen

fun NavController.navigateToSecondScreen(secondRoute: SecondRoute, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = secondRoute) {
        navOptions()
    }
}

fun NavGraphBuilder.secondRoute(
    onSettingsClick: () -> Unit,
) {
    composable<SecondRoute> {
        val args = it.toRoute<SecondRoute>()
        SecondScreen(
            secondRoute = args,
            onSettingsClick = onSettingsClick
        )
    }
}

@Serializable
data object Settings

fun NavController.navigateToSettings() {
    navigate(route = Settings)
}

fun NavGraphBuilder.settings() {
    dialog<Settings> {
        SettingsScreen()
    }
}