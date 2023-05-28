package es.paco.interceptorexample.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import es.paco.interceptorexample.data.repository.remote.mapper.error.ErrorMapper
import es.paco.interceptorexample.data.repository.remote.responses.error.ErrorResponse
import es.paco.interceptorexample.extension.TAG

import okhttp3.ResponseBody

class ErrorsUtils {
    companion object {
        fun generateErrorModelFromResponseErrorBody(responseCode: Int, responseBody: ResponseBody?): es.paco.interceptorexample.data.domain.model.error.ErrorModel {
            val gson = Gson()
            var errorResponse: ErrorResponse? = null
            try {
                errorResponse = gson.fromJson(responseBody?.string(), ErrorResponse::class.java)
            } catch (jsonSyntaxException: JsonSyntaxException) {
                Log.d(TAG, "l> generateErrorModelFromResponseErrorBody problem gson: ${jsonSyntaxException.message}")
            } catch (exception: Exception) {
                Log.d(TAG, "l> generateErrorModelFromResponseErrorBody problem exception: ${exception.message}")
            }

            return if (errorResponse != null) {
                ErrorMapper().fromResponse(errorResponse)
            } else if (responseCode == 401) {
                es.paco.interceptorexample.data.domain.model.error.ErrorModel("401", "401", "401")
            } else {
                es.paco.interceptorexample.data.domain.model.error.ErrorModel()
            }
        }

        fun generateErrorModelFromThrowable(throwable: Throwable): es.paco.interceptorexample.data.domain.model.error.ErrorModel {
            Log.e(TAG, "l> ${throwable.message}")
            return es.paco.interceptorexample.data.domain.model.error.ErrorModel(
                error = "Ups! se ha producido un error",
                errorCode = "",
                message = throwable.localizedMessage.toString()
            )
        }

        fun generateErrorModelFromMessage(message: String): es.paco.interceptorexample.data.domain.model.error.ErrorModel {
            Log.e(TAG, "l> $message")
            return es.paco.interceptorexample.data.domain.model.error.ErrorModel(
                error = "unknow",
                errorCode = "",
                message = message
            )
        }
    }
}