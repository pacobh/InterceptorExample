package es.paco.interceptorexample.data.repository.pokemon

import es.paco.interceptorexample.data.domain.model.error.ErrorModel
import es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonModel
import es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel

interface DataSourceCallbacksPokemon {
    interface GetListPokemonCallback {
        fun onGetListPokemonCallbackSuccess(getListPokemonResponseModel: es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonModel)

        fun onGetListPokemonCallbackUnsuccess(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel)

        fun onGetListPokemonCallbackFailure(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel)
    }

    interface GetPokemonByNameCallback {
        fun onPokemonByNameCallbackSuccess(pokemonInfoModel: es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel)

        fun onPokemonByNameCallbackUnsuccess(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel)

        fun onPokemonByNameCallbackFailure(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel)
    }
}