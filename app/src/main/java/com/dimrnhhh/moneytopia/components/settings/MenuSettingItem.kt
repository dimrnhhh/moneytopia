package com.dimrnhhh.moneytopia.components.settings

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuSettingItem(
    val headlineContent: String,
    val leadingContent: ImageVector,
    val supportingContent: String,
    val onClick: () -> Unit
)