package com.ericg.kripto.domain.repository

import com.ericg.kripto.domain.model.*
import com.ericg.kripto.util.Resource

interface KriptoRepository {
    suspend fun getCoins(): Resource<List<Coin>>

    suspend fun getCoinDetails(coinId: String): Resource<CoinDetails>

    suspend fun getExchanges(): Resource<List<Exchange>>

    suspend fun getExchangeDetails(exchangeId: String): Resource<ExchangeDetails>

    suspend fun getPriceConversion(
        amount: Double,
        fromId: String,
        toId: String
    ): Resource<PriceConversion>
}
