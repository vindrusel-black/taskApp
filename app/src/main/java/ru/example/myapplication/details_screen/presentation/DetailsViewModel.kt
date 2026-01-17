package ru.example.myapplication.details_screen.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.example.myapplication.base.BaseViewModel
import ru.example.myapplication.details_screen.presentation.DetailsContract.Listener
import ru.example.myapplication.main_screen.domain.TaskManager
import java.util.UUID

class DetailsViewModel(
    private val taskId: String? = null,
    private val taskManager: TaskManager,
): BaseViewModel(), Listener {
    private val _viewState = MutableStateFlow(DetailsContract.State())
    val viewState: StateFlow<DetailsContract.State> = _viewState
    private val _effect = MutableSharedFlow<DetailsContract.Effect>()
    val effect: Flow<DetailsContract.Effect> = _effect

    fun setEvent(event: DetailsContract.Event) {
        handleEvents(event = event)
    }

    private fun handleEvents(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.Init -> {}
        }
    }

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

    fun setState(reducer: DetailsContract.State.() -> DetailsContract.State) {
        _viewState.update { it.reducer() }
    }

    fun setEffect(builder: () -> DetailsContract.Effect) = io {
        _effect.emit(builder())
    }

    override fun goBack() {
        setEffect { DetailsContract.Effect.GoBack }
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
