package es.paco.interceptorexample.flows

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventBus {
    private val _events = MutableSharedFlow<Int>()
    val events = _events.asSharedFlow()

    suspend fun startUpdating() {
        while (true) {
            delay(500)
            _events.emit(System.currentTimeMillis().toInt())
        }
    }
}

fun main(): Unit = runBlocking {
    val eventBus = EventBus()

    launch {
        eventBus.startUpdating()
    }

    async {
        eventBus.events.collect() {
            println("Observador 2: $it")
        }
    }

    async {
        eventBus.events.collect() {
            println("Observador 3: $it")
        }
    }

    delay(5000)
    eventBus.events.collect() {
        println(it)
    }
}