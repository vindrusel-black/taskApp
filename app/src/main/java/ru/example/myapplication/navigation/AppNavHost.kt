package ru.example.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.example.myapplication.navigation.screens.composableCreateScreen
import ru.example.myapplication.navigation.screens.composableDetailsScreen
import ru.example.myapplication.navigation.screens.composableMainScreen

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    val start = RouteConfig.MainScreen
    NavHost(
        navController = navController,
        startDestination = start
    ) {
        composableMainScreen(
            innerPadding = innerPadding,
            navController = navController
        )
        composableDetailsScreen(
            innerPadding = innerPadding,
            navController = navController
        )
        composableCreateScreen(
            innerPadding = innerPadding,
            navController = navController
        )
    }
}
