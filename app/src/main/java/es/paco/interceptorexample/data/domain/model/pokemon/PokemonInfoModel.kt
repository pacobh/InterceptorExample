package es.paco.interceptorexample.data.domain.model.pokemon


import es.paco.interceptorexample.data.domain.model.BaseModel

data class PokemonInfoModel(
    val baseExperience: Int = -1,
    val height: Int = -1,
    val id: Int = -1,
    val name: String = "",
    val pokemonInfoSpritesModel: es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoSpritesModel = es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoSpritesModel(),
    val weight: Int = -1
) : es.paco.interceptorexample.data.domain.model.BaseModel()