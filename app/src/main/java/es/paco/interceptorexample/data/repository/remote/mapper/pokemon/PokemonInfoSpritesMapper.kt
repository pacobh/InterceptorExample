package es.paco.interceptorexample.data.repository.remote.mapper.pokemon

import es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoSpritesModel
import es.paco.interceptorexample.data.repository.remote.mapper.ResponseMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoSpritesResponse

class PokemonInfoSpritesMapper :
    ResponseMapper<PokemonInfoSpritesResponse, PokemonInfoSpritesModel> {
    override fun fromResponse(response: PokemonInfoSpritesResponse): PokemonInfoSpritesModel {
        return PokemonInfoSpritesModel(
            response.backDefault ?: "",
            response.backFemale ?: "",
            response.backShiny ?: "",
            response.backShinyFemale ?: "",
            response.frontDefault ?: "",
            response.frontFemale ?: "",
            response.frontShiny ?: "",
            response.frontShinyFemale ?: ""
        )
    }
}