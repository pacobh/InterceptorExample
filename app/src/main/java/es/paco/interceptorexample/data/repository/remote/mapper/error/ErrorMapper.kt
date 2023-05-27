package es.paco.interceptorexample.data.repository.remote.mapper.error

import es.paco.interceptorexample.data.domain.model.error.ErrorModel
import es.paco.interceptorexample.data.repository.remote.mapper.ResponseMapper
import es.paco.interceptorexample.data.repository.remote.responses.error.ErrorResponse

class ErrorMapper : ResponseMapper<ErrorResponse, ErrorModel> {
    override fun fromResponse(response: ErrorResponse): ErrorModel {
        return ErrorModel(
            response.error ?: "",
            response.errorCode ?: "",
            response.message ?: ""
        )
    }
}