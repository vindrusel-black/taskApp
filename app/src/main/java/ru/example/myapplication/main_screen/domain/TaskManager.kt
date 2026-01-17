package ru.example.myapplication.main_screen.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Task

interface TaskManager {
    suspend fun loadTasks()
    fun addNewTask(task: Task)
    fun updateTask(task: Task)
    fun removeTaskById(taskId: String)
    fun getTasks(): List<Task>
    fun getTasksStateFlow(): StateFlow<List<Task>>
}

class TaskManagerImpl: TaskManager {
    private val tasks = MutableStateFlow<List<Task>>(emptyList())

    override fun addNewTask(task: Task) {
        tasks.update { it.plus(task) }
    }

    override fun updateTask(task: Task) {
        tasks.update { list ->
            list.map {
                if (it.id == task.id) task else it
            }
        }
    }

    override fun removeTaskById(taskId: String) {
        tasks.update { tasks ->
            tasks.filterNot { it.id == taskId }
        }
    }

    override fun getTasks() = tasks.value

    override fun getTasksStateFlow() = tasks.asStateFlow()

    override suspend fun loadTasks() {
        tasks.emit(fakeLoadTasks())
    }

    private fun fakeLoadTasks() =
        listOf(
            Task(
                id = "0",
                title = "task1",
                description = "desc1",
            ),
            Task(
                id = "1",
                title = "task2",
                description = "desc2",
            ),
            Task(
                id = "2",
                title = "task3",
                description = "desc3",
            ),
            Task(
                id = "3",
                title = "task4",
                description = "desc4",
            ),
            Task(
                id = "4",
                title = "task5",
                description = "desc5",
            ),
            Task(
                id = "5",
                title = "task6",
                description = "desc6",
            ),
            Task(
                id = "6",
                title = "task7",
                description = "desc7",
            ),
            Task(
                id = "7",
                title = "task8",
                description = "desc8",
            ),
            Task(
                id = "8",
                title = "task9",
                description = "desc9",
            ),
            Task(
                id = "9",
                title = "task10",
                description = "desc10",
            ),
            Task(
                id = "10",
                title = "task11",
                description = "desc11",
            ),
            Task(
                id = "11",
                title = "task12",
                description = "desc12",
            ),
            Task(
                id = "12",
                title = "task13",
                description = "desc13",
            ),
            Task(
                id = "13",
                title = "task14",
                description = "desc14",
            )
        )
}
