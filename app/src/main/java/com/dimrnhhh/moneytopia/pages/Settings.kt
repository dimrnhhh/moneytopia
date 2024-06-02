package com.dimrnhhh.moneytopia.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.header.AlertDialogInfo
import com.dimrnhhh.moneytopia.components.settings.MenuSettingItem
import com.dimrnhhh.moneytopia.components.header.HeaderPage
import com.dimrnhhh.moneytopia.database.realm
import com.dimrnhhh.moneytopia.models.Expense
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    var deleteAlertDialog by remember {
        mutableStateOf(false)
    }
    val eraseAllData: () -> Unit = {
        coroutineScope.launch {
            realm.write {
                val expenses = this.query<Expense>().find()
                delete(expenses)
                deleteAlertDialog = false
            }
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            val openAlertDialog = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HeaderPage(
                    icon = Icons.Filled.Settings,
                    title = stringResource(R.string.settings_title),
                    onClick = { openAlertDialog.value = true }
                )
                if(openAlertDialog.value) {
                    AlertDialogInfo(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = stringResource(R.string.settings_title),
                        dialogText = stringResource(R.string.settings_desc)
                    )
                }
            }
        }
    ) { contentPadding ->
        val items = listOf(
            MenuSettingItem(
                headlineContent = stringResource(R.string.del_button),
                leadingContent = Icons.Outlined.Delete,
                supportingContent = stringResource(R.string.del_desc),
                onClick = { deleteAlertDialog = true }
            ),
//            MenuSettingItem(
//                headlineContent = stringResource(R.string.lang_button),
//                leadingContent = Icons.Outlined.Language,
//                supportingContent = stringResource(R.string.lang_desc),
//                onClick = { navController.navigate("settings/languages") }
//            ),
            MenuSettingItem(
                headlineContent = stringResource(R.string.about_button),
                leadingContent = Icons.Outlined.Info,
                supportingContent = stringResource(R.string.app_name),
                onClick = { navController.navigate("settings/about") }
            )
        )
        
        if(deleteAlertDialog) {
            AlertDialog(
                onDismissRequest = { deleteAlertDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.delete_transactions),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.delete_desc),
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    FilledTonalButton(
                        onClick = {
                            eraseAllData()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.resources.getString(R.string.transaction_deleted)
                                )
                            }
                        },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.delete_confirmation)
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { deleteAlertDialog = false }
                    ) {
                        Text(
                            text = stringResource(R.string.delete_cancel),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large),
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                headlineContent = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 5.dp),
                        text = stringResource(R.string.warning),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                colors = ListItemDefaults.colors(MaterialTheme.colorScheme.errorContainer)
            )
            items.forEach {
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .clickable(
                            onClick = it.onClick
                        ),
                    headlineContent = {
                        Text(
                            text = it.headlineContent,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.supportingContent
                        )
                        },
                    leadingContent = {
                        Icon(
                            imageVector = it.leadingContent,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 88.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(hostState = snackbarHostState) {
                    Snackbar(
                        snackbarData = it,
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                }
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