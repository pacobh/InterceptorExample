package es.paco.interceptorexample.data.repository.remote.mapper.pokemon

import es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonResultModel
import es.paco.interceptorexample.data.repository.remote.mapper.ResponseMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.GetListPokemonResultResponse

class GetListPokemonResultMapper : ResponseMapper<GetListPokemonResultResponse, GetListPokemonResultModel> {
    override fun fromResponse(response: GetListPokemonResultResponse): GetListPokemonResultModel {
        return GetListPokemonResultModel(
            response.name ?: "",
            response.url ?: ""
        )
    }
}