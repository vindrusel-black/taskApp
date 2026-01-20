package ru.example.myapplication.main_screen.presentation

import androidx.compose.runtime.Immutable
import ru.example.myapplication.base.ViewEvent
import ru.example.myapplication.base.ViewSideEffect
import ru.example.myapplication.base.ViewState

interface MainScreenContract {

    @Immutable
    sealed interface Event: ViewEvent

    @Immutable
    data class State(
        val tasks: List<Task> = emptyList()
    ): ViewState

    @Immutable
    sealed interface Effect: ViewSideEffect {
        data class NavigateToDetails(val taskId: String): Effect
        data object NavigateToCreateTask: Effect
    }

    @Immutable
    data class Task(
        val id: String = "",
        val title: String = "",
        val description: String = "",
    )

    interface Listener {
        fun onAddNewTask()
        fun onDeleteClick(taskId: String)
        fun onItemClick(taskId: String)
    }
}
