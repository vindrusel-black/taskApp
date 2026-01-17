package ru.example.myapplication.main_screen.presentation

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.example.myapplication.base.BaseViewModel
import ru.example.myapplication.main_screen.domain.TaskManager
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Effect
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Event
import ru.example.myapplication.main_screen.presentation.MainScreenContract.State

@Stable
class MainViewModel(
    private val taskManager: TaskManager
): BaseViewModel(), MainScreenContract.Listener {
    private val _viewState: MutableStateFlow<State> = MutableStateFlow(State())
    val viewState: StateFlow<State> = _viewState
    private val _effect = MutableSharedFlow<Effect>()
    val effect: Flow<Effect> = _effect

    fun setEvent(event: Event) {
        handleEvents(event)
    }

    fun setState(reducer: State.() -> State) {
        _viewState.update { it.reducer() }
    }

    fun setEffect(builder: () -> Effect) = io {
            _effect.emit(builder())
        }

    private fun handleEvents(event: Event) {}

    init {
        io { taskManager.loadTasks() }
        io {
            taskManager.getTasksStateFlow().collect { tasks ->
                setState { copy(tasks = tasks) }
            }
        }
    }

    // LISTENER
    override fun onDeleteClick(taskId: String) {
        taskManager.removeTaskById(taskId = taskId)
    }

    override fun onItemClick(taskId: String) {
        setEffect { Effect.NavigateToDetails(taskId = taskId) }
    }

    override fun onAddNewTask() {
        setEffect { Effect.NavigateToCreateTask }
    }
}
