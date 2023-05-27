package es.paco.interceptorexample.data.domain.model.error

import es.paco.interceptorexample.data.domain.model.BaseModel

class ErrorModel(
    var error: String = "unknow",
    var errorCode: String = "",
    var message: String = "unknow"
) : es.paco.interceptorexample.data.domain.model.BaseModel() {}