package ru.example.myapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewState
interface ViewEvent
interface ViewSideEffect

abstract class BaseViewModel<Event: ViewEvent, UiState: ViewState, Effect: ViewSideEffect>(
    initialState: UiState
): ViewModel() {
    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()
    private val _effect = MutableSharedFlow<Effect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val effect = _effect.asSharedFlow()

    abstract fun handleEvents(event: Event)

    fun setEvent(event: Event) = io {
        handleEvents(event)
    }

    fun setEffect(builder: () -> Effect) = io {
        _effect.emit(builder())
    }

    fun setState(reducer: UiState.() -> UiState) {
        _viewState.update { it.reducer() }
    }

    fun getState() = _viewState.value

    protected fun io(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(Dispatchers.IO) { block() }
}
