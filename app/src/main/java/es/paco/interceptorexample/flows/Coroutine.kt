package es.paco.interceptorexample.flows

import kotlinx.coroutines.*

fun mainCoroutine() = runBlocking {

    GlobalScope.launch {
        async { } //Lanzar tareas en paralelo
        async { } //Asi ahorramos tiempo
        val result = doAsync()
        println(result)
    }

}

suspend fun doAsync() = withContext(Dispatchers.IO) {
    1
}