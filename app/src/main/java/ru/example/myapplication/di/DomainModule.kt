package ru.example.myapplication.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.example.myapplication.main_screen.domain.TaskManager
import ru.example.myapplication.main_screen.domain.TaskManagerImpl

val domainModule =
    module {
        singleOf(::TaskManagerImpl) { bind<TaskManager>() }
    }
