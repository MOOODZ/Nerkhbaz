package ir.moodz.sarafkoochooloo.data.network.util

import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import timber.log.Timber
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall (execute: () -> HttpResponse): Result<T, DataError.Network>{
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException){
        Timber.e(e)
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException){
        Timber.e(e)
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: ConnectTimeoutException){
        Timber.e(e)
        return Result.Error(DataError.Network.CONNECT_TIMEOUT)
    } catch (e: Exception){
        coroutineContext.ensureActive()
        Timber.e(e)
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network>{
    return when (response.status.value){
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String{
    val baseUrl = "https://moodz.ir/backend/api/v1/store"
    return when{
        route.contains(baseUrl) -> route
        route.startsWith("/") -> baseUrl + route
        else -> "$baseUrl/$route"
    }
}