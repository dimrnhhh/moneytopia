package com.dimrnhhh.moneytopia.pages

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.expenses.ExpensesByAll
import com.dimrnhhh.moneytopia.components.header.AlertDialogInfo
import com.dimrnhhh.moneytopia.components.header.HeaderPage
import com.dimrnhhh.moneytopia.models.Recurrence
import com.dimrnhhh.moneytopia.viewmodels.ExpensesViewModel

@Composable
fun ReportsPage(
    navController: NavController,
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
                    icon = Icons.Filled.Description,
                    title = stringResource(R.string.reports_title),
                    onClick = { openAlertDialog.value = true }
                )
                if(openAlertDialog.value) {
                    AlertDialogInfo(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = stringResource(R.string.reports_title),
                        dialogText = stringResource(R.string.reports_desc)
                    )
                }
            }
        }
    ) { contentPadding ->
        viewModel.setRecurrence(Recurrence.Yearly)
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
                    .verticalScroll(rememberScrollState())
            ) {
                ExpensesByAll(
                    navController = navController,
                    expenses = state.expenses
                )
                Spacer(modifier = Modifier.height(88.dp))
            }
        }
        BackHandler {
            navController.navigate("expenses") {
                popUpTo("expenses") {
                    inclusive = true
                }
            }
        }
    }
}