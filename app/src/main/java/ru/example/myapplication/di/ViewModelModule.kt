package ru.example.myapplication.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.example.myapplication.details_screen.presentation.DetailsViewModel
import ru.example.myapplication.main_screen.presentation.MainViewModel

val viewModelModule =
    module {
        viewModelOf(::MainViewModel)
        viewModel(named("details_edit")) { (taskId: String) ->
            DetailsViewModel(taskId = taskId, taskManager = get())
        }
        viewModel(named("details_create")) { DetailsViewModel(taskManager = get()) }
    }
