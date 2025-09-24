package com.dimrnhhh.moneytopia.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dimrnhhh.moneytopia.R

@Composable
fun AlertDialogCurrency(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    currentSymbol: String
) {
    var newSymbol by remember { mutableStateOf(currentSymbol) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.currency_dialog_title)) },
        text = {
            Column {
                Text(text = stringResource(R.string.currency_dialog_desc))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newSymbol,
                    onValueChange = { newSymbol = it },
                    label = { Text(stringResource(R.string.currency_symbol_label)) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(newSymbol)
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.save_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.discard_cancel_button))
            }
        }
    )
}
