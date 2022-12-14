package com.ericg.kripto.domain.use_case.search_coin

import com.ericg.kripto.domain.model.Coin
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchCoinUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(params: String): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading<List<Coin>>())

        when (val coins = repository.searchCoin(params)) {
            is Resource.Success -> emit(coins)
            else -> {
                emit(Resource.Error<List<Coin>>(data = null, coins.message!!))
            }
        }
    }
}
