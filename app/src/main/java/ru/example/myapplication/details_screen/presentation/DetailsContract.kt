package ru.example.myapplication.details_screen.presentation

import androidx.compose.runtime.Immutable
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Task

interface DetailsContract {

    @Immutable
    sealed interface Event {
        data object Init: Event
    }

    @Immutable
    data class State(
        val task: Task = Task()
    )

    @Immutable
    sealed interface Effect {
        data object GoBack: Effect
    }

    interface Listener {
        fun goBack()
        fun onSaveClick()
        fun onTitleChange(title: String)
        fun onDescriptionChange(description: String)
    }
}
