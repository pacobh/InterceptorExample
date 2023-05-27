package es.paco.interceptorexample.data.repository.remote.pokemon

import android.util.Log
import es.paco.interceptorexample.data.repository.pokemon.DataSourceCallbacksPokemon
import es.paco.interceptorexample.data.repository.remote.mapper.pokemon.GetListPokemonMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.GetListPokemonResponse
import es.paco.interceptorexample.extension.TAG
import es.paco.interceptorexample.utils.ErrorsUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCallbacksPokemon {
    companion object {
        fun getListPokemonCallback(getListPokemonCallback: DataSourceCallbacksPokemon.GetListPokemonCallback): Callback<GetListPokemonResponse> {
            return object : Callback<GetListPokemonResponse> {
                override fun onResponse(call: Call<GetListPokemonResponse>, response: Response<GetListPokemonResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.i(TAG, "l> Éxito en la respuesta de getListPokemonCallback.")
                        getListPokemonCallback.onGetListPokemonCallbackSuccess(GetListPokemonMapper().fromResponse(response.body()!!))
                    } else {
                        Log.e(TAG, "l> Problemas en la respuesta de getListPokemonCallback.")
                        getListPokemonCallback.onGetListPokemonCallbackUnsuccess(
                            ErrorsUtils.generateErrorModelFromResponseErrorBody(
                                response.code(),
                                response.errorBody()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<GetListPokemonResponse>, throwable: Throwable) {
                    Log.e(TAG, "l> Problemas en la respuesta de getListPokemonCallback failure.")
                    getListPokemonCallback.onGetListPokemonCallbackFailure(ErrorsUtils.generateErrorModelFromThrowable(throwable))
                }
            }
        }
    }
}