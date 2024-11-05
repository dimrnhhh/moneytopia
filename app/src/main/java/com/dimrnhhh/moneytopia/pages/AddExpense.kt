package com.dimrnhhh.moneytopia.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.models.Category
import com.dimrnhhh.moneytopia.models.getName
import com.dimrnhhh.moneytopia.viewmodels.AddExpenseViewModel
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddExpensePage(
    navController: NavController,
    viewModel: AddExpenseViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val categories = listOf(
        Category.Bills.getName(),
        Category.Debt.getName(),
        Category.Education.getName(),
        Category.Family.getName(),
        Category.FoodsAndDrinks.getName(),
        Category.Healthcare.getName(),
        Category.Savings.getName(),
        Category.Shopping.getName(),
        Category.SocialEvents.getName(),
        Category.TopUp.getName(),
        Category.Transportation.getName(),
        Category.Others.getName(),
    )
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedCategory by remember {
        mutableStateOf(categories[0])
    }
    val categoryIcon = if (expanded) {
        Icons.Outlined.ArrowDropUp
    } else {
        Icons.Outlined.ArrowDropDown
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val dateIcon = if (showDatePicker) {
        Icons.Outlined.ArrowDropUp
    } else {
        Icons.Outlined.ArrowDropDown
    }
    val openAlertDialog = remember {
        mutableStateOf(false)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val controller = LocalSoftwareKeyboardController.current
    val maxInt = 10
    val maxChar = 20

    @Composable
    fun OpenAlertDialog() {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            text = {
                Text(text = stringResource(R.string.discard_transaction))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog.value = false
                        navController.navigate("expenses") {
                            popUpTo("expenses") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.discard_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openAlertDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.discard_cancel_button))
                }
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_expense_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(state.amount != "" ||  state.note != "") {
                                openAlertDialog.value = true
                            } else {
                                navController.navigate("expenses") {
                                    popUpTo("expenses") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                        if(openAlertDialog.value) {
                            OpenAlertDialog()
                        }
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.submitExpense()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.resources.getString(R.string.transaction_saved)
                                )
                            }
                            controller?.hide()
                        },
                        enabled = state.amount != "" && state.category != "" && state.note != ""
                    ) {
                        Text(text = stringResource(R.string.save_button))
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Payment,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.amount_label))
                },
                value = state.amount,
                onValueChange = {
                    if (it.length <= maxInt) {
                        viewModel.setAmount(it)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                viewModel.setCategory(selectedCategory)
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Category,
                            contentDescription = null)
                    },
                    label = { Text(text = stringResource(R.string.category_label)) },
                    value = selectedCategory,
                    onValueChange = { selectedCategory = it },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = categoryIcon,
                                contentDescription = null
                            )
                        }
                    }
                )
                DropdownMenu(
                    modifier = Modifier
                        .exposedDropdownSize(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                selectedCategory = it
                                viewModel.setCategory(selectedCategory)
                                expanded = false
                            }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = showDatePicker,
                onExpandedChange = { showDatePicker = !showDatePicker }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Today,
                            contentDescription = null
                        )
                    },
                    value = state.date.toString(),
                    onValueChange = {  },
                    label = { Text(text = stringResource(R.string.date_label)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                imageVector = dateIcon,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
            if(showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    onDateChange = {
                        viewModel.setDate(it)
                        showDatePicker = false
                    },
                    initialDate = state.date,
                    title = {
                        Text(text = stringResource(R.string.date_title))
                    }
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.StickyNote2,
                        contentDescription = null
                    )
                },
                value = state.note,
                onValueChange = {
                    if (it.length <= maxChar) {
                        viewModel.setNote(it)
                    }
                },
                singleLine = true,
                label = {
                    Text(text = stringResource(R.string.note_label))
                },
                trailingIcon = {
                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp),
                        text = "${state.note.length}/$maxChar",
                        textAlign = TextAlign.End
                    )
                },
            )
        }
        BackHandler {
            if(state.amount != "" || state.note != "") {
                openAlertDialog.value = true
            } else {
                navController.navigate("expenses") {
                    popUpTo("expenses") {
                        inclusive = true
                    }
                }
            }
        }
        if(openAlertDialog.value) {
            OpenAlertDialog()
        }
    }
}