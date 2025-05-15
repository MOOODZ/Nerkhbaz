package ir.moodz.sarafkoochooloo.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Parameters
import ir.moodz.sarafkoochooloo.data.mapper.asDomain
import ir.moodz.sarafkoochooloo.data.mapper.toCurrency
import ir.moodz.sarafkoochooloo.data.mapper.toCurrencyDetail
import ir.moodz.sarafkoochooloo.data.network.model.CheckUpdateDataDto
import ir.moodz.sarafkoochooloo.data.network.model.CheckUpdateResponseDto
import ir.moodz.sarafkoochooloo.data.network.model.CurrenciesDetailResponseDto
import ir.moodz.sarafkoochooloo.data.network.model.CurrenciesResponseDto
import ir.moodz.sarafkoochooloo.data.network.util.constructRoute
import ir.moodz.sarafkoochooloo.data.network.util.safeCall
import ir.moodz.sarafkoochooloo.domain.model.CheckUpdate
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyDetail
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import ir.moodz.sarafkoochooloo.domain.util.map

class KtorRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteDataSource {

    override suspend fun getCurrencies(selectedCurrency: String): Result<List<Currency>, DataError.Network> {
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
        }.map { it.currenciesDataDto.priceDtoList.map { it.toCurrency() } }
    }

    override suspend fun getCurrencyInfoByDays(currencyTitle: String): Result<List<CurrencyDetail>, DataError.Network> {
        return safeCall<CurrenciesDetailResponseDto> {
            httpClient.post {
                url(constructRoute("/detailCurrency"))
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append(
                                name = "id",
                                value = currencyTitle
                            )
                        }
                    )
                )
            }
        }.map { it.currencyDetailData.detailedCurrencyPrices.map { it.toCurrencyDetail() } }
    }

    override suspend fun isAppVersionValid(versionCode: Int): Result<CheckUpdate, DataError.Network> {
        return safeCall<CheckUpdateDataDto> {
            httpClient.post {
                url(constructRoute("/checkUpdateSaraf"))
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append(
                                name = "version_code",
                                value = versionCode.toString()
                            )
                        }
                    )
                )
            }
        }.map { it.data.update.asDomain() }
    }
}