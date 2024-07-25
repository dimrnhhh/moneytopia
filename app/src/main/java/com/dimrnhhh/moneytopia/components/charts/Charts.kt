package com.dimrnhhh.moneytopia.components.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.viewmodels.viewModelFactory
import com.dimrnhhh.moneytopia.components.expenses.ExpensesByPeriod
import com.dimrnhhh.moneytopia.models.Recurrence
import com.dimrnhhh.moneytopia.utils.formatDayForRange
import com.dimrnhhh.moneytopia.viewmodels.ChartsViewModel
import java.text.DecimalFormat
import java.time.LocalDate

@Composable
fun Charts(
    navController: NavController,
    page: Int,
    recurrence: Recurrence,
    viewModel: ChartsViewModel = viewModel(
        key = "$page-${recurrence.name}",
        factory = viewModelFactory {
            ChartsViewModel(page, recurrence)
        }
    )
) {
    val uiState = viewModel.uiState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${uiState.dateStart.formatDayForRange()} - ${uiState.dateEnd.formatDayForRange()}",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(R.string.currency) + DecimalFormat(stringResource(R.string.number_format)).format(uiState.totalInRange),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            when (recurrence) {
                Recurrence.Weekly -> WeeklyChart(expenses = uiState.expenses)
                Recurrence.Monthly -> MonthlyChart(
                    expenses = uiState.expenses,
                    LocalDate.now()
                )
                Recurrence.Yearly -> YearlyChart(expenses = uiState.expenses)
                else -> WeeklyChart(expenses = uiState.expenses)
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            ExpensesByPeriod(
                navController = navController,
                expenses = uiState.expenses
            )
            Spacer(modifier = Modifier.height(88.dp))
        }
    }
}