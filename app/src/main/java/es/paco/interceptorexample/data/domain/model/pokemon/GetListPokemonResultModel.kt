package es.paco.interceptorexample.data.domain.model.pokemon

import es.paco.interceptorexample.data.domain.model.BaseModel

data class GetListPokemonResultModel(
    val name: String = "",
    val url: String = ""
) : es.paco.interceptorexample.data.domain.model.BaseModel()