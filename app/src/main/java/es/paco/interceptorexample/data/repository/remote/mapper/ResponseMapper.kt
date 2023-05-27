package es.paco.interceptorexample.data.repository.remote.mapper

interface ResponseMapper<E, M> {
    fun fromResponse(response: E): M
}