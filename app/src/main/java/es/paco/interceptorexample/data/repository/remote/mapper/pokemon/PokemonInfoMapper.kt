package es.paco.interceptorexample.data.repository.remote.mapper.pokemon

import es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel
import es.paco.interceptorexample.data.repository.remote.mapper.ResponseMapper
import es.paco.interceptorexample.data.repository.remote.responses.pokemon.PokemonInfoResponse

class PokemonInfoMapper : ResponseMapper<PokemonInfoResponse, PokemonInfoModel> {
    override fun fromResponse(response: PokemonInfoResponse): es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel {

        val pokemonInfoSpritesModel = if (response.pokemonInfoSpritesResponse != null) {
            PokemonInfoSpritesMapper().fromResponse(response.pokemonInfoSpritesResponse)
        } else {
            es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoSpritesModel()
        }

        return es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel(
            response.baseExperience ?: -1,
            response.height ?: -1,
            response.id ?: -1,
            response.name ?: "",
            pokemonInfoSpritesModel,
            response.weight ?: -1
        )
    }
}