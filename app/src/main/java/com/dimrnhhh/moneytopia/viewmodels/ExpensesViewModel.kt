package com.dimrnhhh.moneytopia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimrnhhh.moneytopia.database.realm
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.models.Recurrence
import com.dimrnhhh.moneytopia.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpensesState(
    val recurrence: Recurrence = Recurrence.Daily,
    val expenses: List<Expense> = listOf(),
    val sumTotal: Double = 0.0
)

class ExpensesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesState())
    val uiState: StateFlow<ExpensesState> = _uiState.asStateFlow()
    init {
        _uiState.update {
            it.copy(
                expenses = realm.query<Expense>().find()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            realm.query<Expense>().asFlow().collect {
                _uiState.update { state ->
                    state.copy(
                        expenses = it.list,
                        sumTotal = it.list.sumOf { it.amount }
                    )
                }
                setRecurrence(Recurrence.Daily)
            }
        }
    }
    fun setRecurrence(recurrence: Recurrence) {
        val (start, end) = calculateDateRange(recurrence, 0)
        val filteredExpenses = realm.query<Expense>().find().filter {
            (it.date.toLocalDate().isAfter(start) && it.date.toLocalDate()
                .isBefore(end)) || it.date.toLocalDate()
                .isEqual(start) || it.date.toLocalDate().isEqual(end)
        }
        val sumTotal = filteredExpenses.sumOf { it.amount }
        _uiState.update {
            it.copy(
                recurrence = recurrence,
                expenses = filteredExpenses,
                sumTotal = sumTotal
            )
        }
    }
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.write {
                val deleteExpense = this.query<Expense>("_id == $0", expense._id).find().first()
                delete(deleteExpense)
            }
        }
    }
}
