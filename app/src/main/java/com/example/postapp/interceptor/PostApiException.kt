package com.example.postapp.interceptor

internal sealed class PostApiException(message: String? = null, cause: Throwable? = null) :
    Throwable(message, cause) {

    object NetworkError : PostApiException()

    class ServerError internal constructor(cause: ApiRequestException.BadResponse) :
        PostApiException(cause = cause)

    class InvalidRequestError internal constructor(cause: ApiRequestException.BadRequest) :
        PostApiException(cause = cause) {
        override val cause: ApiRequestException.BadRequest
            get() = super.cause as ApiRequestException.BadRequest
    }

    class ParsingError internal constructor(cause: Throwable) : PostApiException(cause = cause)

}
