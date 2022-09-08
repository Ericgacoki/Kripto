package com.ericg.kripto.domain.use_case.get_exchanges

import com.ericg.kripto.domain.model.Exchange
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(): Flow<Resource<List<Exchange>>> = flow {
        emit(Resource.Loading<List<Exchange>>())

        when (val result = repository.getExchanges()) {
            is Resource.Success -> emit(result)
            else -> emit(
                Resource.Error<List<Exchange>>(
                    message = result.message ?: "An error occurred when fetching exchanges!"
                )
            )
        }
    }
}
