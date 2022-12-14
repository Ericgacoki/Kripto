package com.ericg.kripto.presentation.screen.coin_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.model.Coin
import com.ericg.kripto.domain.use_case.get_coins.GetCoinsUseCase
import com.ericg.kripto.domain.use_case.search_coin.SearchCoinUseCase
import com.ericg.kripto.presentation.screen.coin_list.event.CoinListUiEvent
import com.ericg.kripto.presentation.screen.coin_list.state.CoinListState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val searchCoinsUseCase: SearchCoinUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<CoinListState> = MutableStateFlow(CoinListState())
    var state: StateFlow<CoinListState> = _state.asStateFlow()
        private set

    private var initialCoins = listOf<Coin>()
    var searchParams: String = ""
        private set

    init {
        onEvent(CoinListUiEvent.GetCoins)
    }

    fun onEvent(event: CoinListUiEvent) {
        when (event) {
            is CoinListUiEvent.GetCoins -> {
                getCoinsUseCase().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val coins = result.data ?: emptyList()
                            _state.value =
                                CoinListState(coins = coins)
                            initialCoins = result.data ?: emptyList()
                        }
                        is Resource.Error -> {
                            _state.value = CoinListState(
                                error = result.message!!
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = CoinListState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is CoinListUiEvent.SearchCoin -> {
                if (event.searchParams.isNotEmpty()) {
                    searchParams = event.searchParams.trim()

                    searchCoinsUseCase(searchParams).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = CoinListState(coins = result.data ?: emptyList())
                            }
                            is Resource.Error -> {
                                _state.value = CoinListState(
                                    error = result.message!!
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = CoinListState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                } else {
                    searchParams = ""
                    _state.value = CoinListState(coins = initialCoins.take(500))
                }
            }
        }
    }
}
