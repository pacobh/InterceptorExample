package es.paco.interceptorexample.data.repository.remote.responses.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") var error: String?,
    @SerializedName("errorCode") var errorCode: String?,
    @SerializedName("message") var message: String?
)