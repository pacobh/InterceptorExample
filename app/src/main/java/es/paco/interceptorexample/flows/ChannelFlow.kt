package es.paco.interceptorexample.flows

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ChannelFlow {
    private val _channel = Channel<Int>()
    val channel = _channel.receiveAsFlow()

    suspend fun startUpdating() {
//        while (true) {
        delay(2000)
        _channel.send(System.currentTimeMillis().toInt())
//        }
    }
}

fun mainChannel(): Unit = runBlocking {
    val channelFlow = ChannelFlow()

    launch {
        channelFlow.startUpdating()
    }

    async {
        channelFlow.channel.collect() {
            println("Observador 1: $it")
        }
    }

    async {
        delay(2000)
        channelFlow.channel.collect() {
            println("Observador 2: $it")
        }
    }

//    delay(3000)
    channelFlow.channel.collect() {
        println("Observador 3: $it")
    }
}