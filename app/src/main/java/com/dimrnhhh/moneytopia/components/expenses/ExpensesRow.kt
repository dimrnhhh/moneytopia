package com.dimrnhhh.moneytopia.components.expenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingBag
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
import com.dimrnhhh.moneytopia.models.Expense
import com.dimrnhhh.moneytopia.viewmodels.ExpensesViewModel
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesRow(
    navController: NavController,
    expense: Expense,
    viewModel: ExpensesViewModel = viewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val categoryIcon = when (expense.category) {
        stringResource(R.string.bills) -> {
            Icons.Outlined.Payment
        }
        stringResource(R.string.debt) -> {
            Icons.Outlined.CreditCard
        }
        stringResource(R.string.education) -> {
            Icons.Outlined.School
        }
        stringResource(R.string.entertainment) -> {
            Icons.Outlined.ConfirmationNumber
        }
        stringResource(R.string.family) -> {
            Icons.Outlined.FamilyRestroom
        }
        stringResource(R.string.foods_and_drinks) -> {
            Icons.Outlined.Restaurant
        }
        stringResource(R.string.healthcare) -> {
            Icons.Outlined.HealthAndSafety
        }
        stringResource(R.string.savings) -> {
            Icons.Outlined.Savings
        }
        stringResource(R.string.shopping) -> {
            Icons.Outlined.ShoppingBag
        }
        stringResource(R.string.top_up) -> {
            Icons.Outlined.AddCard
        }
        stringResource(R.string.transportation) -> {
            Icons.Outlined.LocalTaxi
        }
        else -> {
            Icons.Outlined.Category
        }
    }
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
            Icon(
                imageVector = categoryIcon,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(
                modifier = Modifier
                    .padding(bottom = 3.dp),
                text = expense.category,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        },
        supportingContent = {
            Text(
                text = expense.note,
                style = MaterialTheme.typography.titleSmall
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = stringResource(R.string.total_currency) + DecimalFormat(stringResource(R.string.number_format)).format(expense.amount),
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
                        color = MaterialTheme.colorScheme.error
                    )
                }
            )
        }
    }
}