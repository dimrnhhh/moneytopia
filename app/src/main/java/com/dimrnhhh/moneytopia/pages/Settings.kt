package com.dimrnhhh.moneytopia.pages

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dimrnhhh.moneytopia.R
import com.dimrnhhh.moneytopia.components.header.AlertDialogInfo
import com.dimrnhhh.moneytopia.components.settings.MenuSettingItem
import com.dimrnhhh.moneytopia.components.header.HeaderPage
import com.dimrnhhh.moneytopia.components.settings.AlertDialogDelete
import com.dimrnhhh.moneytopia.database.realm
import com.dimrnhhh.moneytopia.models.Expense
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsPage(
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    var deleteAlertDialog by remember {
        mutableStateOf(false)
    }
    var languageAlertDialog by remember {
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
    val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    Scaffold(
        topBar = {
            val openAlertDialog = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
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
        val items = mutableListOf(
            MenuSettingItem(
                headlineContent = stringResource(R.string.del_button),
                leadingContent = Icons.Outlined.Delete,
                supportingContent = stringResource(R.string.del_desc),
                onClick = { deleteAlertDialog = true }
            ),
            MenuSettingItem(
                headlineContent = stringResource(R.string.lang_button),
                leadingContent = Icons.Outlined.Language,
                supportingContent = stringResource(R.string.lang_desc),
                onClick = { languageAlertDialog = true }
            ),
            MenuSettingItem(
                headlineContent = stringResource(R.string.about_button),
                leadingContent = Icons.Outlined.Info,
                supportingContent = stringResource(R.string.app_name),
                onClick = { navController.navigate("settings/about") }
            )
        )

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            items.removeAt(1)
        }

        if(deleteAlertDialog) {
            AlertDialogDelete(
                icon = Icons.Outlined.Delete,
                onDismissRequest = { deleteAlertDialog = false },
                onConfirmation = {
                    eraseAllData()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.resources.getString(R.string.transaction_deleted)
                        )
                    }
                },
                dialogTitle = stringResource(R.string.delete_transactions),
                dialogText = stringResource(R.string.delete_desc),
                buttonText = stringResource(R.string.delete_confirmation)
            )
        }

        if(languageAlertDialog) {
            AlertDialogDelete(
                icon = Icons.Outlined.Error,
                onDismissRequest = { languageAlertDialog = false },
                onConfirmation = {
                    eraseAllData()
                    context.startActivity(intent)
                    languageAlertDialog = false
                },
                dialogTitle = stringResource(R.string.caution),
                dialogText = stringResource(R.string.caution_desc),
                buttonText = stringResource(R.string.caution_confirmation)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            ListItem(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(MaterialTheme.shapes.large),
//                leadingContent = {
//                    Icon(
//                        imageVector = Icons.Outlined.Warning,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.error
//                    )
//                },
//                headlineContent = {
//                    Text(
//                        modifier = Modifier
//                            .padding(vertical = 8.dp),
//                        text = stringResource(R.string.warning),
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                },
//                colors = ListItemDefaults.colors(MaterialTheme.colorScheme.errorContainer)
//            )
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