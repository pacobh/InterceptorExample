package es.paco.interceptorexample.data.repository.remote.responses.pokemon

import com.google.gson.annotations.SerializedName

data class GetListPokemonResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<GetListPokemonResultResponse>?
)