package es.paco.interceptorexample.data.repository.remote.mapper

interface RequestMapper<M, E> {
    fun toRequest(model: M): E
}