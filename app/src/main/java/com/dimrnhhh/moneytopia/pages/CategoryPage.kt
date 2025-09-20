package com.dimrnhhh.moneytopia.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.categories.IconPickerDialog
import com.dimrnhhh.moneytopia.components.categories.getIconVector
import com.dimrnhhh.moneytopia.models.Category
import com.dimrnhhh.moneytopia.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPage(
    navController: NavController,
    viewModel: CategoryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val openAddCategoryDialog = remember { mutableStateOf(false) }
    val openEditCategoryDialog = remember { mutableStateOf<Category?>(null) }
    val openDeleteCategoryDialog = remember { mutableStateOf<Category?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.category_label),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openAddCategoryDialog.value = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            LazyColumn {
                items(uiState.categories) { category ->
                    ListItem(
                        headlineContent = { Text(text = category.name) },
                        leadingContent = {
                            Icon(
                                imageVector = getIconVector(category.icon),
                                contentDescription = null
                            )
                        },
                        trailingContent = {
                            Row {
                                IconButton(onClick = { openEditCategoryDialog.value = category }) {
                                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                                }
                                IconButton(onClick = { openDeleteCategoryDialog.value = category }) {
                                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    if (openAddCategoryDialog.value) {
        AddCategoryDialog(
            onDismiss = { openAddCategoryDialog.value = false },
            onConfirm = { name, icon ->
                viewModel.addCategory(name, icon)
                openAddCategoryDialog.value = false
            }
        )
    }

    openEditCategoryDialog.value?.let { category ->
        EditCategoryDialog(
            category = category,
            onDismiss = { openEditCategoryDialog.value = null },
            onConfirm = { name, icon ->
                viewModel.updateCategory(category, name, icon)
                openEditCategoryDialog.value = null
            }
        )
    }

    openDeleteCategoryDialog.value?.let { category ->
        DeleteCategoryDialog(
            category = category,
            onDismiss = { openDeleteCategoryDialog.value = null },
            onConfirm = {
                viewModel.deleteCategory(category)
                openDeleteCategoryDialog.value = null
            }
        )
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("Category") }
    var showIconPicker by remember { mutableStateOf(false) }

    if (showIconPicker) {
        IconPickerDialog(
            onDismiss = { showIconPicker = false },
            onIconSelected = {
                selectedIcon = it.name
                showIconPicker = false
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Category") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Category Name") }
                )
                IconButton(onClick = { showIconPicker = true }) {
                    Icon(imageVector = getIconVector(selectedIcon), contentDescription = "Selected Icon")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, selectedIcon) },
                enabled = name.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditCategoryDialog(
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(category.name) }
    var selectedIcon by remember { mutableStateOf(category.icon) }
    var showIconPicker by remember { mutableStateOf(false) }

    if (showIconPicker) {
        IconPickerDialog(
            onDismiss = { showIconPicker = false },
            onIconSelected = {
                selectedIcon = it.name
                showIconPicker = false
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Category") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Category Name") }
                )
                IconButton(onClick = { showIconPicker = true }) {
                    Icon(imageVector = getIconVector(selectedIcon), contentDescription = "Selected Icon")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, selectedIcon) },
                enabled = name.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DeleteCategoryDialog(
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete Category") },
        text = { Text(text = "Are you sure you want to delete ${category.name}?") },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}