package com.ericg.kripto.domain.use_case.get_coins

import com.ericg.kripto.domain.model.Coin
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading<List<Coin>>())

        when (val coins = repository.getCoins()) {
            is Resource.Success -> emit(coins)
            else -> {
                emit(Resource.Error<List<Coin>>(data = null, coins.message!!))
            }
        }
    }
}
