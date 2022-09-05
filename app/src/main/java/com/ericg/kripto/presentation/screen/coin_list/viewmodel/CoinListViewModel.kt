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

    /**
     * NOTE:
     *     Since the API doesn't support paging, the coins endpoint returns OVER 8000 items
     *     at once which is irrelevant to display on the UI. For this reason,
     *     this function only takes the first 500 items ONLY from the database.
     *     Well, that's not my fault!
     */

    fun onEvent(event: CoinListUiEvent) {
        when (event) {
            is CoinListUiEvent.GetCoins -> {
                getCoinsUseCase().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            val coins = result.data?.take(500) ?: emptyList()
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

                    /*_state.value = _state.value.copy(coins = initialCoins.filter { coin ->
                        (coin.name.contains(event.searchParams.trim(), ignoreCase = true)) ||
                                (coin.rank.toString()
                                    .contains(event.searchParams.trim(), ignoreCase = true))
                    })*/

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
