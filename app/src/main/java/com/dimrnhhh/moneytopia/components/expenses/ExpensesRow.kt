package com.dimrnhhh.moneytopia.components.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.categories.getIconVector
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.viewmodels.CategoryViewModel
import com.dimrnhhh.moneytopia.viewmodels.ExpensesViewModel
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesRow(
    navController: NavController,
    expense: Expense,
    viewModel: ExpensesViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val categoryState by categoryViewModel.uiState.collectAsState()

    val category = categoryState.categories.find { it.name == expense.category }
    val icon = getIconVector(category?.icon ?: "Category")

    val expensesState by viewModel.uiState.collectAsState()

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable {
                when (backStackEntry?.destination?.route) {
                    "expenses" -> {
                        showBottomSheet.value = true
                    }

                    "reports" -> {
                        showBottomSheet.value = false
                    }

                    "analytics" -> {
                        showBottomSheet.value = false
                    }
                }
            },
        leadingContent = {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(10.dp),
                    imageVector = icon,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        },
        headlineContent = {
            Column {
                Text(
                    modifier = Modifier
                        .padding(bottom = 3.dp),
                    text = expense.category,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                if (expense.note.isNotEmpty()) {
                    Text(
                        text = expense.note,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        supportingContent = { },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "- " + expensesState.currencySymbol + DecimalFormat(stringResource(R.string.number_format)).format(expense.amount),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = expense.date.format(DateTimeFormatter.ofPattern(stringResource(R.string.time_format))),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        colors = ListItemDefaults.colors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    )
    if(showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            ListItem(
                modifier = Modifier
                    .clickable {
                        viewModel.deleteExpense(expense)
                        showBottomSheet.value = false
                    },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                headlineContent = {
                    Text(
                        text = stringResource(R.string.delete_transaction),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            )
        }
    }
}