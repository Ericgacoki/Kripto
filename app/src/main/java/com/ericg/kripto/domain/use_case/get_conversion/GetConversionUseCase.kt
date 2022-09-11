package com.ericg.kripto.domain.use_case.get_conversion

import com.ericg.kripto.domain.model.PriceConversion
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetConversionUseCase @Inject constructor(
    private val repository: KriptoRepository
) {
    operator fun invoke(
        amount: Double,
        baseCurrencyId: String,
        quoteCurrencyId: String
    ): Flow<Resource<PriceConversion>> = flow {
        emit(Resource.Loading<PriceConversion>())
        when (val result = repository.getPriceConversion(amount, baseCurrencyId, quoteCurrencyId)) {
            is Resource.Success<PriceConversion> -> {
                emit(result)
            }
            else -> {
                emit(Resource.Error<PriceConversion>(message = result.message!!))
            }
        }
    }
}
