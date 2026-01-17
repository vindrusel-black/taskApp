package ru.example.myapplication.main_screen.presentation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

interface ViewState

interface ViewEvent

interface ViewSideEffect

interface MainScreenContract {
    sealed interface Event: ViewEvent {}

    data class State(
        val tasks: List<Task> = emptyList()
    ): ViewState

    sealed interface Effect: ViewSideEffect {
        data class NavigateToDetails(val taskId: String): Effect
        data object NavigateToCreateTask: Effect
    }

    data class Task(
        val id: String = "",
        val title: String = "",
        val description: String = "",
    )

    @Stable
    interface Listener {
        fun onAddNewTask()
        fun onDeleteClick(taskId: String)
        fun onItemClick(taskId: String)
    }
}
