package es.paco.interceptorexample.flows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun mainExample() = runBlocking {
    makeFlow4()
        .filter { it % 2 == 0 }
        .map { "Value: $it" }
        .collect {
            println(it)
        }

}

fun makeFlow() = listOf(1, 2, 3, 4).asFlow()

fun makeFlow2() = flowOf(1, 2, 3, 4)

fun makeFlow3() = flow {
    for (i in 0..10) {
        doAsync2()
        emit(i)
    }
}

suspend fun doAsync2() = withContext(Dispatchers.IO) {
    //Llamada a servidor
}

fun makeFlow4() = flow {
    for (i in 0..10) {
        delay(500)
        emit(i)
    }
}