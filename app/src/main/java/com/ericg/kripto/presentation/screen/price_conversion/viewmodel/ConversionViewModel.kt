package com.ericg.kripto.presentation.screen.price_conversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.use_case.get_conversion.GetConversionUseCase
import com.ericg.kripto.presentation.screen.price_conversion.event.ConversionUiEvent
import com.ericg.kripto.presentation.screen.price_conversion.state.ConversionState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val getConversionUseCase: GetConversionUseCase
) : ViewModel() {
    private var _conversionState: MutableStateFlow<ConversionState> =
        MutableStateFlow(ConversionState.NotLoading)
    var conversionState = _conversionState.asStateFlow()

    init {
        onEvent(ConversionUiEvent.ResetState)
    }

    fun onEvent(event: ConversionUiEvent) {
        when (event) {
            is ConversionUiEvent.OnConvert -> {
                getConversionUseCase(
                    event.amount,
                    event.baseCurrencyId,
                    event.quoteCurrencyId
                ).onEach {
                    when (it) {
                        is Resource.Success -> {
                            _conversionState.value = ConversionState.Success(data = it.data!!)
                        }
                        is Resource.Loading -> {
                            _conversionState.value = ConversionState.Loading
                        }
                        is Resource.Error -> {
                            _conversionState.value = ConversionState.Error(message = it.message!!)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is ConversionUiEvent.ResetState -> {
                _conversionState.value = ConversionState.NotLoading
            }
        }
    }
}
