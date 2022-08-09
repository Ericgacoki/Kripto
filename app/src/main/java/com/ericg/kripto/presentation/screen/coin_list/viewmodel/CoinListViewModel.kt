package com.ericg.kripto.presentation.screen.coin_list.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.use_case.get_coins.GetCoinsUseCase
import com.ericg.kripto.presentation.screen.coin_list.state.CoinListState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {
    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }
    /**
     * NB:
     *
     *     Since the API doesn't support paging, the /coins endpoint
     *     returns around 5K items at once which is irrelevant to display
     *     on the UI. For this reason, this function only takes the first
     *     500 items from the Flow.
     */
    fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data?.take(500) ?: emptyList())
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

}