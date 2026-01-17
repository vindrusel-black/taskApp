package ru.example.myapplication.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RouteConfig {
    @Serializable
    data object MainScreen: RouteConfig

    @Serializable
    data class DetailsScreen(
        val id: String,
    ): RouteConfig

    @Serializable
    data object CreateTaskScreen: RouteConfig
}
