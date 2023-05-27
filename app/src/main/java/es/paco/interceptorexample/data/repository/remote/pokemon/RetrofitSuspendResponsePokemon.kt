package es.paco.interceptorexample.data.repository.remote.pokemon

import android.util.Log
import es.paco.interceptorexample.data.repository.pokemon.DataSourceCallbacksPokemon
import es.paco.interceptorexample.data.repository.remote.mapper.pokemon.PokemonInfoMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoResponse
import es.paco.interceptorexample.extension.TAG
import es.paco.interceptorexample.utils.ErrorsUtils
import retrofit2.Response

class RetrofitSuspendResponsePokemon {
    companion object {
        fun analizeGetPokemonbyNameCallback(
            getPokemonByNameCallback: DataSourceCallbacksPokemon.GetPokemonByNameCallback,
            response: Response<PokemonInfoResponse>
        ) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    Log.i(TAG, "l> Éxito en la respuesta de analizeGetPokemonbyNameCallback.")
                    getPokemonByNameCallback.onPokemonByNameCallbackSuccess(
                        PokemonInfoMapper().fromResponse(response.body()!!)
                    )
                } else {
                    Log.e(TAG, "l> Problemas en la respuesta de analizeGetPokemonbyNameCallback.")
                    getPokemonByNameCallback.onPokemonByNameCallbackUnsuccess(
                        ErrorsUtils.generateErrorModelFromResponseErrorBody(response.code(), response.errorBody())
                    )
                }
            } else {
                Log.e(TAG, "l> Problemas en la respuesta de analizeGetPokemonbyNameCallback failure.")
                getPokemonByNameCallback.onPokemonByNameCallbackFailure(
                    ErrorsUtils.generateErrorModelFromResponseErrorBody(response.code(), response.errorBody())
                )
            }
        }
    }

}