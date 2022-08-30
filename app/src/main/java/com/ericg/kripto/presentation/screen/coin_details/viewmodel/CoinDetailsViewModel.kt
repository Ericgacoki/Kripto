package com.ericg.kripto.presentation.screen.coin_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.kripto.domain.use_case.get_coin_details.GetCoinDetailsUseCase
import com.ericg.kripto.presentation.screen.coin_details.state.CoinDetailsState
import com.ericg.kripto.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CoinDetailsState> = MutableStateFlow(CoinDetailsState())
    var state: StateFlow<CoinDetailsState> = _state.asStateFlow()
        private set

    fun getCoinDetails(coinId: String) {
        getCoinDetailsUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = CoinDetailsState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = CoinDetailsState(coinDetails = result.data)
                }
                is Resource.Error -> {
                    _state.value = CoinDetailsState(
                        error = result.message!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
