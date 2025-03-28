package ir.moodz.sarafkoochooloo.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

object HttpClientFactory {

    fun build(): HttpClient{
        return HttpClient(CIO){
            install(HttpTimeout){
                requestTimeoutMillis = 30000L
                connectTimeoutMillis = 10000L
                socketTimeoutMillis = 30000L
            }
            install(ContentNegotiation){
                json( json = Json{ ignoreUnknownKeys = true } )
            }
            install(Logging){
                logger = object : Logger{
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
            defaultRequest {
                contentType(ContentType.Application.FormUrlEncoded)
            }
        }
    }
}