package es.paco.interceptorexample.data.domain.model.pokemon


import es.paco.interceptorexample.data.domain.model.BaseModel

data class PokemonInfoSpritesModel(
    val backDefault: String = "",
    val backFemale: String = "",
    val backShiny: String = "",
    val backShinyFemale: String = "",
    val frontDefault: String = "",
    val frontFemale: String = "",
    val frontShiny: String = "",
    val frontShinyFemale: String = ""
) : es.paco.interceptorexample.data.domain.model.BaseModel()