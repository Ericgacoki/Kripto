package com.ericg.kripto.domain.use_case.get_coin_details

import com.ericg.kripto.domain.model.CoinDetails
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinDetailsUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetails>> = flow {
        emit(Resource.Loading<CoinDetails>())

        when (val coinDetails = repository.getCoinDetails(coinId)) {
            is Resource.Success -> emit(coinDetails)
            else -> {
                emit(Resource.Error<CoinDetails>(data = null, coinDetails.message!!))
            }
        }
    }
}