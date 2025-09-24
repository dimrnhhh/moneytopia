package com.dimrnhhh.moneytopia.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dimrnhhh.moneytopia.database.realm
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.models.Recurrence
import com.dimrnhhh.moneytopia.utils.PreferenceManager
import com.dimrnhhh.moneytopia.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

data class ChartsState(
    val expenses: List<Expense> = listOf(),
    val dateStart: LocalDateTime = LocalDateTime.now(),
    val dateEnd: LocalDateTime = LocalDateTime.now(),
    val totalInRange: Double = 0.0,
    val currencySymbol: String = "$"
)

class ChartsViewModel(
    application: Application,
    private val page: Int,
    val recurrence: Recurrence
): AndroidViewModel(application) {
    private val preferenceManager = PreferenceManager(application)
    private val _uiState = MutableStateFlow(ChartsState())
    val uiState: StateFlow<ChartsState> = _uiState.asStateFlow()

    init {
        loadCurrencySymbol()
        viewModelScope.launch(Dispatchers.IO) {
            val (start, end) = calculateDateRange(recurrence, page)
            val filteredExpenses = realm.query<Expense>().find().filter {
                (it.date.toLocalDate().isAfter(start) && it.date.toLocalDate()
                    .isBefore(end)) || it.date.toLocalDate()
                    .isEqual(start) || it.date.toLocalDate().isEqual(end)
            }
            val totalExpensesAmount = filteredExpenses.sumOf { it.amount }
            _uiState.update {
                it.copy(
                    dateStart = LocalDateTime.of(start, LocalTime.MIN),
                    dateEnd = LocalDateTime.of(end, LocalTime.MAX),
                    expenses = filteredExpenses,
                    totalInRange = totalExpensesAmount,
                )
            }
        }
    }

    private fun loadCurrencySymbol() {
        viewModelScope.launch {
            val symbol = preferenceManager.getCurrencySymbol()
            _uiState.update { it.copy(currencySymbol = symbol) }
        }
    }
}
