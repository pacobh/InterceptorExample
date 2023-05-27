package es.paco.interceptorexample.data.repository.remote.mapper.pokemon

import es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonModel
import es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonResultModel
import es.paco.interceptorexample.data.repository.remote.mapper.ResponseMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.GetListPokemonResponse

class GetListPokemonMapper : ResponseMapper<GetListPokemonResponse, GetListPokemonModel> {
    override fun fromResponse(response: GetListPokemonResponse): GetListPokemonModel {

        val resultModel = arrayListOf<GetListPokemonResultModel>()

        if (!response.results.isNullOrEmpty()) {
            val getListPokemonResultMapper = GetListPokemonResultMapper()
            response.results.forEach { getListPokemonResultResponse ->
                resultModel.add(getListPokemonResultMapper.fromResponse(getListPokemonResultResponse))
            }
        }

        return GetListPokemonModel(
            response.count ?: -1,
            response.next ?: "",
            response.previous ?: "",
            resultModel
        )
    }
}