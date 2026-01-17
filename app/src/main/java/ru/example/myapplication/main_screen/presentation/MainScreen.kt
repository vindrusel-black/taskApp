package ru.example.myapplication.main_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.example.myapplication.main_screen.presentation.MainScreenContract.Task
import ru.example.myapplication.navigation.RouteConfig
import ru.example.myapplication.ui.theme.MyApplicationTheme
import ru.example.myapplication.utils.EffectHandler

@Composable
fun MainScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    entry: NavBackStackEntry
) {
    val viewModel: MainViewModel = koinViewModel(viewModelStoreOwner = entry)
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    EffectHandler(viewModel.effect) { effect ->
        when (effect) {
            is MainScreenContract.Effect.NavigateToDetails -> {
                navController.navigate(RouteConfig.DetailsScreen(id = effect.taskId))
            }
            MainScreenContract.Effect.NavigateToCreateTask ->
                navController.navigate(RouteConfig.CreateTaskScreen)
        }
    }

    MainScreenContent(
        innerPadding = innerPadding,
        tasks = state.tasks,
        onAddNewTaskClick = viewModel::onAddNewTask,
        onItemClick = { viewModel.onItemClick(taskId = it.id) },
        onDeleteTaskClick = viewModel::onDeleteClick
    )
}

@Composable
fun MainScreenContent(
    innerPadding: PaddingValues,
    tasks: List<Task>,
    onAddNewTaskClick: () -> Unit,
    onItemClick: (Task) -> Unit,
    onDeleteTaskClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Text(
            text = "Добавь новую таску!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(color = Color.Green)
                .clickable { onAddNewTaskClick() }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = tasks,
                key = { it.id }
            ) {
                TaskItem(
                    title = it.title,
                    onItemClick = { onItemClick(it) },
                    onDeleteClick = { onDeleteTaskClick(it.id) }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    title: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onItemClick() }
            .background(color = Color.Cyan),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(
            text = "УдОли!",
            modifier = Modifier
                .clickable { onDeleteClick() }
                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskItemPreview() {
    MyApplicationTheme {
        MainScreenContent(
            innerPadding = PaddingValues(top = 24.dp),
            tasks = listOf(Task(id = "0", title = "Title1", description = "descr")),
            onAddNewTaskClick = {},
            onItemClick = {},
            onDeleteTaskClick = {}
        )
    }
}
