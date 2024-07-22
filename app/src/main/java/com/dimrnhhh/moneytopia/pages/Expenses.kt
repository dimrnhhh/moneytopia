package com.dimrnhhh.moneytopia.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.expenses.ExpensesByDay
import com.dimrnhhh.moneytopia.components.header.AlertDialogInfo
import com.dimrnhhh.moneytopia.components.header.HeaderPage
import com.dimrnhhh.moneytopia.viewmodels.ExpensesViewModel
import java.text.DecimalFormat

@Composable
fun ExpensesPage(
    navController: NavHostController,
    viewModel: ExpensesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            val openAlertDialog = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderPage(
                    icon = Icons.Filled.AccountBalanceWallet,
                    title = stringResource(R.string.expenses_title),
                    onClick = { openAlertDialog.value = true }
                )
                if(openAlertDialog.value) {
                    AlertDialogInfo(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = stringResource(R.string.expenses_title),
                        dialogText = stringResource(R.string.expenses_desc)
                    )
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.total_expenses),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.currency) + DecimalFormat(stringResource(R.string.number_format)).format(state.sumTotal),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ExpensesByDay(
                    expenses = state.expenses,
                    navController = navController
                )
                Spacer(modifier = Modifier.height(88.dp))
            }
        }
    }
}