package com.example.postapp.interceptor

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import java.io.IOException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import okhttp3.Response

sealed class ApiRequestException : IOException() {
    class BadRequest(response: Response) : ApiRequestException() {
        val code = response.code

        override val message by Message(response)
    }

    class BadResponse(response: Response) : ApiRequestException() {
        val code = response.code

        override val message by Message(response)
    }

    object NetworkError : ApiRequestException()
}

private class Message(response: Response) : ReadOnlyProperty<Any?, String> {

    private val status = "${response.code} ${response.message}"
    private val body = response.body?.string() ?: ""
    private val originalUrl = response.request.url

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "status='$status', body=$body, requestUrl=$originalUrl"
    }
}

@Throws(PostApiException::class)
internal suspend fun <T> performNetworkCall(action: suspend () -> T): T {
    return try {
        action()
    } catch (e: ApiRequestException) {
        throw e.toPostException()
    } catch (e: JsonParseException) {
        throw PostApiException.ParsingError(e)
    } catch (e: MalformedJsonException) {
        throw PostApiException.ParsingError(e)
    }
}

private fun ApiRequestException.toPostException() = when (this) {
    is ApiRequestException.BadRequest -> PostApiException.InvalidRequestError(this)
    is ApiRequestException.BadResponse -> PostApiException.ServerError(this)
    ApiRequestException.NetworkError -> PostApiException.NetworkError
}
