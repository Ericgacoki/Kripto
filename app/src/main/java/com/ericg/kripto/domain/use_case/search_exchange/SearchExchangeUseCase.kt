package com.ericg.kripto.domain.use_case.search_exchange

import com.ericg.kripto.domain.model.Exchange
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchExchangeUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(params: String): Flow<Resource<List<Exchange>>> = flow {
        emit(Resource.Loading<List<Exchange>>())

        when (val result = repository.searchExchange(params)) {
            is Resource.Success -> emit(result)
            else -> emit(
                Resource.Error<List<Exchange>>(
                    data = null,
                    message = result.message ?: "An Error Occurred When Searching!"
                )
            )
        }
    }
}