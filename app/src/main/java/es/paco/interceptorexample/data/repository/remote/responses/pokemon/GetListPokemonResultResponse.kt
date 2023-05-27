package es.paco.interceptorexample.data.repository.remote.responses.pokemon

import com.google.gson.annotations.SerializedName

data class GetListPokemonResultResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)