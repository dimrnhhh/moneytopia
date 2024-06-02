package com.dimrnhhh.moneytopia.viewmodels

import androidx.lifecycle.ViewModel
import com.dimrnhhh.moneytopia.models.Recurrence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AnalyticsState(
    val recurrence: Recurrence = Recurrence.Weekly,
    val recurrenceMenuOpened: Boolean = false
)

class AnalyticsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AnalyticsState())
    val uiState: StateFlow<AnalyticsState> = _uiState.asStateFlow()
    fun setRecurrence(recurrence: Recurrence) {
        _uiState.update { currentState ->
            currentState.copy(
                recurrence = recurrence
            )
        }
    }
}