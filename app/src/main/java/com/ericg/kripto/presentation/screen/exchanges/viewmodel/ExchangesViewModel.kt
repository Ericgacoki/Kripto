package com.ericg.kripto.presentation.screen.exchanges.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.model.Exchange
import com.ericg.kripto.domain.use_case.get_exchanges.GetExchangesUseCase
import com.ericg.kripto.presentation.screen.exchanges.event.ExchangesUiEvent
import com.ericg.kripto.presentation.screen.exchanges.state.ExchangesState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExchangesViewModel @Inject constructor(
    private val exchangesUseCase: GetExchangesUseCase
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
                            val exchanges = result.data?.take(500) ?: emptyList()
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

                    _state.value = ExchangesState(isLoading = true)

                    val searchResult = initialExchanges.filter {
                        it.name?.contains(searchParams, ignoreCase = true) ?: false
                    }
                    _state.value = ExchangesState(exchanges = searchResult)

                } else {
                    searchParams = ""
                    _state.value = ExchangesState(exchanges = initialExchanges.take(500))
                }
            }
        }
    }
}
