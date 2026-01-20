package ru.example.myapplication.main_screen.presentation

import ru.example.myapplication.base.BaseViewModel
import ru.example.myapplication.main_screen.domain.TaskManager
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Effect
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Event
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Listener
import ru.example.myapplication.main_screen.presentation.MainScreenContract.State

class MainViewModel(
    private val taskManager: TaskManager
): BaseViewModel<Event, State, Effect>(initialState = State()), Listener {
    override fun handleEvents(event: Event) {}

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
