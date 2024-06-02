package com.dimrnhhh.moneytopia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _isReady = MutableStateFlow(true)
    val isReady = _isReady.asStateFlow()
    init {
        viewModelScope.launch {
            delay(1500L)
            _isReady.value = false
        }
    }
}