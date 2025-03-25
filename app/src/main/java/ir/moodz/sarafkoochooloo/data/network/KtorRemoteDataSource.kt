package ir.moodz.sarafkoochooloo.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Parameters
import ir.moodz.sarafkoochooloo.data.network.model.CurrenciesResponseDto
import ir.moodz.sarafkoochooloo.data.network.util.constructRoute
import ir.moodz.sarafkoochooloo.data.network.util.safeCall
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.data.mapper.toCurrency
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import ir.moodz.sarafkoochooloo.domain.util.map

class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun getPrices(selectedCurrency: String): Result<List<Currency>, DataError.Network> {
        return safeCall<CurrenciesResponseDto> {
            httpClient.post {
                url(constructRoute("/homesaraf"))
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append(
                                name = "currency",
                                value = selectedCurrency
                            )
                        }
                    )
                )
            }
        }.map { it.priceData.priceList.map { it.toCurrency() } }
    }
}