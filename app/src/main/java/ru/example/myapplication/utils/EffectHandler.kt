package ru.example.myapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <E> EffectHandler(
    flow: Flow<E>,
    onEffect: (E) -> Unit
) {
    LaunchedEffect(flow) {
        flow.collect { onEffect(it) }
    }
}