package com.ericg.kripto.data.remote

import com.ericg.kripto.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("coins/{coin_id}")
    suspend fun getCoinDetails(
        @Path("coin_id") coinId: String
    ): CoinDetailsDto

    @GET("exchanges")
    suspend fun getExchanges(): List<ExchangeDto>

    @GET("exchanges/{exchange_id}")
    suspend fun getExchangeDetails(
        @Path("exchange_id") exchangeId: String
    ): ExchangeDetailsDto

    @GET("price-converter")
    suspend fun getPriceConversion(
        @Query("base_currency_id") baseCurrencyId: String,
        @Query("amount") amount: Double,
        @Query("quote_currency_id") quoteCurrencyId: String
    ): PriceConversionDto
}
