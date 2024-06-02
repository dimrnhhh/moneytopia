package com.dimrnhhh.moneytopia.components.expenses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.models.groupedByDay
import com.dimrnhhh.moneytopia.utils.formatDay

@Composable
fun ExpensesByPeriod(
    navController: NavController,
    expenses: List<Expense>
) {
    val groupedExpenses = expenses.groupedByDay()

    if(groupedExpenses.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedExpenses.keys.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.formatDay(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                ExpensesDayGroup(
                    navController = navController,
                    date = it,
                    dayExpenses = groupedExpenses[it]!!
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.empty_analytics),
                textAlign = TextAlign.Center
            )
        }
    }
}