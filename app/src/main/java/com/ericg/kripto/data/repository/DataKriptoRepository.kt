package com.ericg.kripto.data.repository

import com.ericg.kripto.data.local.database.KriptoDatabase
import com.ericg.kripto.data.remote.api.ApiService
import com.ericg.kripto.domain.model.*
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.mapper.*
import com.ericg.kripto.util.Resource
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class DataKriptoRepository @Inject constructor(
    private val api: ApiService,
    private val kriptoDB: KriptoDatabase
) : KriptoRepository {
    override suspend fun getCoins(): Resource<List<Coin>> {
        return try {
            var coinsDb = kriptoDB.coinDao.getCoins().map { it.toCoin() }

            if (coinsDb.isEmpty()) {
                val coinsRemote = api.getCoins()
                coinsDb = coinsRemote.map { it.toCoin() } // Next time use a Flow to observe the DB
                kriptoDB.coinDao.insertCoins(coinsRemote.map { it.toCoinEntity() })
            }

            Resource.Success<List<Coin>>(data = coinsDb)
        } catch (e: IOException) {
            Timber.e(message = "Couldn't reach server. Check your internet connection!")
            Resource.Error<List<Coin>>(message = "Couldn't reach server. Check your internet connection!")

        } catch (e: HttpException) {
            Timber.e(message = e.localizedMessage ?: "An unexpected error occurred!")
            Resource.Error<List<Coin>>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun searchCoin(params: String): Resource<List<Coin>> {
        return try {
            val result = kriptoDB.coinDao.searchCoin(params).map { it.toCoin() }
            Resource.Success<List<Coin>>(data = result)
        } catch (e: Exception){
            Resource.Error<List<Coin>>(message = "Fatal Database Error Occurred!")
        }
    }

    override suspend fun getCoinDetails(coinId: String): Resource<CoinDetails> {
        return try {
            val coinDetails = api.getCoinDetails(coinId).toCoinDetails()
            Resource.Success<CoinDetails>(data = coinDetails)

        } catch (e: IOException) {
            Timber.e(message = "Couldn't reach server. Check your internet connection!")
            Resource.Error<CoinDetails>(message = "Couldn't reach server. Check your internet connection!")

        } catch (e: HttpException) {
            Timber.e(message = e.localizedMessage ?: "An unexpected error occurred!")
            Resource.Error<CoinDetails>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getExchanges(): Resource<List<Exchange>> {
        return try {
            val exchanges = api.getExchanges().map { it.toExchange() }
            Resource.Success<List<Exchange>>(data = exchanges)

        } catch (e: IOException) {
            Timber.e(message = "Couldn't reach server. Check your internet connection!")
            Resource.Error<List<Exchange>>(message = "Couldn't reach server. Check your internet connection!")

        } catch (e: HttpException) {
            Timber.e(message = e.localizedMessage ?: "An unexpected error occurred!")
            Resource.Error<List<Exchange>>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getExchangeDetails(exchangeId: String): Resource<ExchangeDetails> {
        return try {
            val exchangeDetails = api.getExchangeDetails(exchangeId).toExchangeDetails()
            Resource.Success<ExchangeDetails>(data = exchangeDetails)

        } catch (e: IOException) {
            Timber.e(message = "Couldn't reach server. Check your internet connection!")
            Resource.Error<ExchangeDetails>(message = "Couldn't reach server. Check your internet connection!")

        } catch (e: HttpException) {
            Timber.e(message = e.localizedMessage ?: "An unexpected error occurred!")
            Resource.Error<ExchangeDetails>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }

    override suspend fun getPriceConversion(
        amount: Double,
        fromId: String,
        toId: String
    ): Resource<PriceConversion> {
        return try {
            val priceConversion = api.getPriceConversion(
                amount = amount,
                baseCurrencyId = fromId,
                quoteCurrencyId = toId
            ).toPriceConversion()
            Resource.Success<PriceConversion>(data = priceConversion)

        } catch (e: IOException) {
            Timber.e(message = "Couldn't reach server. Check your internet connection!")
            Resource.Error<PriceConversion>(message = "Couldn't reach server. Check your internet connection!")

        } catch (e: HttpException) {
            Timber.e(message = e.localizedMessage ?: "An unexpected error occurred!")
            Resource.Error<PriceConversion>(
                message = e.localizedMessage ?: "An unexpected error occurred!"
            )
        }
    }
}
