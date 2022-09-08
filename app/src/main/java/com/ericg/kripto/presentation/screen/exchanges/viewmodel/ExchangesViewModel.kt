package com.ericg.kripto.presentation.screen.exchanges.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.model.Exchange
import com.ericg.kripto.domain.use_case.get_exchanges.GetExchangesUseCase
import com.ericg.kripto.domain.use_case.search_exchange.SearchExchangeUseCase
import com.ericg.kripto.presentation.screen.exchanges.event.ExchangesUiEvent
import com.ericg.kripto.presentation.screen.exchanges.state.ExchangesState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    private val exchangesUseCase: GetExchangesUseCase,
    private val searchExchangeUseCase: SearchExchangeUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ExchangesState> = MutableStateFlow(ExchangesState())
    var state: StateFlow<ExchangesState> = _state.asStateFlow()
        private set

    private var initialExchanges = listOf<Exchange>()
    var searchParams: String = ""
        private set

    init {
        onEvent(ExchangesUiEvent.GetExchanges)
    }

    fun onEvent(event: ExchangesUiEvent) {
        when (event) {
            is ExchangesUiEvent.GetExchanges -> {
                exchangesUseCase().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val exchanges = result.data ?: emptyList()
                            _state.value = ExchangesState(exchanges = exchanges)
                            initialExchanges = result.data ?: emptyList()
                        }
                        is Resource.Error -> {
                            _state.value = ExchangesState(
                                error = result.message!!
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = ExchangesState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }

            is ExchangesUiEvent.SearchExchange -> {
                if (event.searchParams.isNotEmpty()) {
                    searchParams = event.searchParams.trim()

                    searchExchangeUseCase(searchParams).onEach {
                        when(it){
                            is Resource.Loading -> {
                                _state.value = ExchangesState(isLoading = true)
                            }
                            is Resource.Success -> {
                                val result = it.data?.take(250) ?: emptyList()
                                _state.value = ExchangesState(isLoading = false, exchanges = result)
                            }
                            is Resource.Error -> {
                                _state.value = ExchangesState(error = it.message!!)
                            }
                        }
                    }.launchIn(viewModelScope)
                } else {
                    searchParams = ""
                    _state.value = ExchangesState(exchanges = initialExchanges.take(500))
                }
            }
        }
    }
}
