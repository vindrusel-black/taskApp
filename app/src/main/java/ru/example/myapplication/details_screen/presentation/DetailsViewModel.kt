package ru.example.myapplication.details_screen.presentation

import ru.example.myapplication.base.BaseViewModel
import ru.example.myapplication.details_screen.presentation.DetailsContract.Effect
import ru.example.myapplication.details_screen.presentation.DetailsContract.Event
import ru.example.myapplication.details_screen.presentation.DetailsContract.Listener
import ru.example.myapplication.details_screen.presentation.DetailsContract.State
import ru.example.myapplication.main_screen.domain.TaskManager
import java.util.UUID

class DetailsViewModel(
    private val taskId: String? = null,
    private val taskManager: TaskManager
): BaseViewModel<Event, State, Effect>(initialState = State()), Listener {
    override fun handleEvents(event: Event) {}

    init {
        init()
    }

    private fun init() =
        io {
            taskManager
                .getTasksStateFlow()
                .value
                .firstOrNull { it.id == taskId }
                ?.let { setState { copy(task = it) } }
        }

    override fun goBack() {
        setEffect { Effect.GoBack }
    }

    override fun onSaveClick() {
        val task = viewState.value.task
        if (taskId.isNullOrEmpty()) {
            taskManager.addNewTask(task = task.copy(id = UUID.randomUUID().toString()))
        } else {
            taskManager.updateTask(task = task.copy(id = taskId))
        }
    }

    override fun onTitleChange(title: String) {
        setState { copy(task = task.copy(title = title)) }
    }

    override fun onDescriptionChange(description: String) {
        setState { copy(task = task.copy(description = description)) }
    }
}
