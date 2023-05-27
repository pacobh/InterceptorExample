package es.paco.interceptorexample.data.repository.pokemon

import es.paco.interceptorexample.data.repository.remote.pokemon.RemoteDataSourcePokemon
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProviderPokemon @Inject constructor(private val remoteDataSourcePokemon: RemoteDataSourcePokemon) : DataSourcePokemon {
    override fun getListPokemon(getListPokemonCallback: DataSourceCallbacksPokemon.GetListPokemonCallback, limit: Int, offset: Int) {
        remoteDataSourcePokemon.getListPokemon(getListPokemonCallback, limit, offset)
    }

    override suspend fun getPokemonByName(name: String): Response<PokemonInfoResponse> {
        return remoteDataSourcePokemon.getPokemonByName(name)
    }
}