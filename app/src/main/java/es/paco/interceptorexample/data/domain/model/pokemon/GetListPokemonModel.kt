package es.paco.interceptorexample.data.domain.model.pokemon

import es.paco.interceptorexample.data.domain.model.BaseModel

data class GetListPokemonModel(
    val count: Int = -1,
    val next: String = "",
    val previous: String = "",
    val results: List<es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonResultModel> = arrayListOf()
) : es.paco.interceptorexample.data.domain.model.BaseModel()