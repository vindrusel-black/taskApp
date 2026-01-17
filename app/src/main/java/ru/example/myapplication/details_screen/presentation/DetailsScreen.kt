package ru.example.myapplication.details_screen.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.example.myapplication.main_screen.presentation.MainScreenContract
import ru.example.myapplication.navigation.RouteConfig
import ru.example.myapplication.ui.theme.MyApplicationTheme
import ru.example.myapplication.utils.EffectHandler

@Composable
fun CreateTaskScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    entry: NavBackStackEntry
) {
    val viewModel: DetailsViewModel = koinViewModel(
        qualifier = named("details_create"),
        viewModelStoreOwner = entry
    )
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    EffectHandler(viewModel.effect) { effect ->
        when (effect) {
            DetailsContract.Effect.GoBack -> navController.popBackStack()
        }
    }

    DetailsContent(
        state = state,
        innerPadding = innerPadding,
        onBackClick = { viewModel.goBack() },
        onSaveClick = { viewModel.onSaveClick() },
        onTitleChange = { viewModel.onTitleChange(it) },
        onDescriptionChange = { viewModel.onDescriptionChange(it) }
    )
}

@Composable
fun DetailsScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    entry: NavBackStackEntry
) {
    val route = entry.toRoute<RouteConfig.DetailsScreen>()
    val viewModel: DetailsViewModel = koinViewModel(
        qualifier = named("details_edit"),
        viewModelStoreOwner = entry,
        parameters = { parametersOf(route.id) }
    )
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    EffectHandler(viewModel.effect) { effect ->
        when (effect) {
            DetailsContract.Effect.GoBack -> navController.popBackStack()
        }
    }

    DetailsContent(
        state = state,
        innerPadding = innerPadding,
        onBackClick = viewModel::goBack,
        onSaveClick = viewModel::onSaveClick,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange
    )
}

@Composable
fun DetailsContent(
    state: DetailsContract.State,
    innerPadding: PaddingValues,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                content = { Text(text = "Back") },
                onClick = { onBackClick() }
            )
            Button(
                content = { Text(text = "Save") },
                onClick = { onSaveClick() }
            )
        }
        TextField(
            value = state.task.title,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.task.description,
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    MyApplicationTheme {
        val state = DetailsContract.State(
            task = MainScreenContract.Task(
                title = "Title",
                description = "Description text."
            )
        )
        DetailsContent(
            state = state,
            innerPadding = PaddingValues(top = 24.dp),
            onBackClick = {},
            onSaveClick = {},
            onTitleChange = {},
            onDescriptionChange = {}
        )
    }
}
