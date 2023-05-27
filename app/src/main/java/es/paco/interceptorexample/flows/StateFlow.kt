package es.paco.interceptorexample.flows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewState {
    private val _state = MutableStateFlow(1)
    val state = _state.asStateFlow()

    suspend fun startUpdating() {
        while (true) {
            delay(2000)
            _state.value = _state.value + 1
        }
    }
}

fun mainState(): Unit = runBlocking {

    val viewState = ViewState()

    launch {
        viewState.startUpdating()
    }

    delay(7000)
    viewState.state.collect() {
        println(it)
    }

}