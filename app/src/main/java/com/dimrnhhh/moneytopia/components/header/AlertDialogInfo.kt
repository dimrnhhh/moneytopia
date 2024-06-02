package com.dimrnhhh.moneytopia.components.header

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dimrnhhh.moneytopia.R

@Composable
fun AlertDialogInfo(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = dialogTitle,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Text(
                text = dialogText,
                textAlign = TextAlign.Center,
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(
                    text = stringResource(R.string.alert_button),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}