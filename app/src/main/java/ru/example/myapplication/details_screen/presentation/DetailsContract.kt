package ru.example.myapplication.details_screen.presentation

import ru.example.myapplication.main_screen.presentation.MainScreenContract.Task

interface DetailsContract {
    sealed interface Event {
        data object Init: Event
    }

    data class State(
        val task: Task = Task()
    )

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
