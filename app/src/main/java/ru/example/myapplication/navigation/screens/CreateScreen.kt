package ru.example.myapplication.navigation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.example.myapplication.details_screen.presentation.CreateTaskScreen
import ru.example.myapplication.main_screen.presentation.MainScreen
import ru.example.myapplication.navigation.RouteConfig

fun NavGraphBuilder.composableCreateScreen(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    composable<RouteConfig.CreateTaskScreen> {
        CreateTaskScreen(
            innerPadding = innerPadding,
            navController = navController,
            entry = it
        )
    }
}
