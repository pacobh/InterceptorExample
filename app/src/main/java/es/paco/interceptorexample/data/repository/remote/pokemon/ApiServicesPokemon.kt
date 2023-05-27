package es.paco.interceptorexample.data.repository.remote.pokemon

import es.paco.interceptorexample.data.repository.remote.responses.pokemon.GetListPokemonResponse
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiServicesPokemon {
    //API LIST
    //############
    @GET("api/v2/pokemon")
    fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Call<GetListPokemonResponse>

    @GET("api/v2/pokemon/{name}/")
    suspend fun getPokemonbyName(
        @Path("name") name: String,
    ): Response<PokemonInfoResponse>
}