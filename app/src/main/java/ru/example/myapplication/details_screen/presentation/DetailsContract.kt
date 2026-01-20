package ru.example.myapplication.details_screen.presentation

import androidx.compose.runtime.Immutable
import ru.example.myapplication.base.ViewEvent
import ru.example.myapplication.base.ViewSideEffect
import ru.example.myapplication.base.ViewState
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Task

interface DetailsContract {

    @Immutable
    sealed interface Event: ViewEvent

    @Immutable
    data class State(
        val task: Task = Task()
    ): ViewState

    @Immutable
    sealed interface Effect: ViewSideEffect {
        data object GoBack: Effect
    }

    interface Listener {
        fun goBack()
        fun onSaveClick()
        fun onTitleChange(title: String)
        fun onDescriptionChange(description: String)
    }
}
