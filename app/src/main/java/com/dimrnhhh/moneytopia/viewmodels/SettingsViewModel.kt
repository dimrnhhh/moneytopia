package com.dimrnhhh.moneytopia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dimrnhhh.moneytopia.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsState(
    val currencySymbol: String = "$"
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val preferenceManager = PreferenceManager(application)

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        loadCurrencySymbol()
    }

    private fun loadCurrencySymbol() {
        viewModelScope.launch {
            val symbol = preferenceManager.getCurrencySymbol()
            _uiState.update { it.copy(currencySymbol = symbol) }
        }
    }

    fun saveCurrencySymbol(symbol: String) {
        viewModelScope.launch {
            preferenceManager.saveCurrencySymbol(symbol)
            _uiState.update { it.copy(currencySymbol = symbol) }
        }
    }
}
