package es.paco.interceptorexample.data.repository.remote.pokemon

import es.paco.interceptorexample.data.repository.pokemon.DataSourceCallbacksPokemon
import es.paco.interceptorexample.data.repository.pokemon.DataSourcePokemon
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourcePokemon @Inject constructor(private val apiServicePokemon: ApiServicesPokemon) : DataSourcePokemon {
    override fun getListPokemon(getListPokemonCallback: DataSourceCallbacksPokemon.GetListPokemonCallback, limit: Int, offset: Int) {
        val getListPokemonCall = apiServicePokemon.getListPokemon(limit, offset)
        getListPokemonCall.enqueue(RetrofitCallbacksPokemon.getListPokemonCallback(getListPokemonCallback))
    }

    override suspend fun getPokemonByName(name: String): Response<PokemonInfoResponse> {
        return apiServicePokemon.getPokemonbyName(name)
    }
}