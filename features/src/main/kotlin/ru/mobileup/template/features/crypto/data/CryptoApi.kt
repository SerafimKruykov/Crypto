package ru.mobileup.template.features.crypto.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mobileup.template.features.crypto.data.dto.CoinResponseModel
import ru.mobileup.template.features.crypto.data.dto.DetailedCoinResponse


interface CryptoApi {

    @GET("https://api.coingecko.com/api/v3/coins/markets/")
    suspend fun getCoinsByExchange(@Query("vs_currency") exchangeId: String): List<CoinResponseModel>

    @GET("https://api.coingecko.com/api/v3/coins/{coinId}")
    suspend fun getCoinDetails(@Path("coinId") coinId: String): DetailedCoinResponse
}