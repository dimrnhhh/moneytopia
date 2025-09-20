package com.dimrnhhh.moneytopia.components.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class IconItem(val name: String, val imageVector: ImageVector)

val availableIcons = listOf(
    // Finance & Shopping
    IconItem("Payments", Icons.Outlined.Payments),
    IconItem("CreditCard", Icons.Outlined.CreditCard),
    IconItem("ShoppingBag", Icons.Outlined.ShoppingBag),
    IconItem("ShoppingCart", Icons.Outlined.ShoppingCart),
    IconItem("ReceiptLong", Icons.AutoMirrored.Outlined.ReceiptLong),
    IconItem("Savings", Icons.Outlined.Savings),
    IconItem("PointOfSale", Icons.Outlined.PointOfSale),
    IconItem("Paid", Icons.Outlined.Paid),
    IconItem("AccountBalance", Icons.Outlined.AccountBalance),
    IconItem("MonetizationOn", Icons.Outlined.MonetizationOn),

    // Food & Drink
    IconItem("Fastfood", Icons.Outlined.Fastfood),
    IconItem("LocalCafe", Icons.Outlined.LocalCafe),
    IconItem("Restaurant", Icons.Outlined.Restaurant),
    IconItem("LunchDining", Icons.Outlined.LunchDining),
    IconItem("Cake", Icons.Outlined.Cake),
    IconItem("EmojiFoodBeverage", Icons.Outlined.EmojiFoodBeverage),

    // Home & Utilities
    IconItem("Cottage", Icons.Outlined.Cottage),
    IconItem("House", Icons.Outlined.House),
    IconItem("Lightbulb", Icons.Outlined.Lightbulb),
    IconItem("WaterDrop", Icons.Outlined.WaterDrop),
    IconItem("Wifi", Icons.Outlined.Wifi),
    IconItem("Phone", Icons.Outlined.Phone),
    IconItem("Build", Icons.Outlined.Build),

    // Transportation
    IconItem("LocalTaxi", Icons.Outlined.LocalTaxi),
    IconItem("DirectionsCar", Icons.Outlined.DirectionsCar),
    IconItem("LocalGasStation", Icons.Outlined.LocalGasStation),
    IconItem("Train", Icons.Outlined.Train),
    IconItem("Flight", Icons.Outlined.Flight),

    // Health & Wellness
    IconItem("MonitorHeart", Icons.Outlined.MonitorHeart),
    IconItem("FitnessCenter", Icons.Outlined.FitnessCenter),
    IconItem("Spa", Icons.Outlined.Spa),
    IconItem("MedicalServices", Icons.Outlined.MedicalServices),

    // Entertainment & Social
    IconItem("Diversity1", Icons.Outlined.Diversity1),
    IconItem("Theaters", Icons.Outlined.Theaters),
    IconItem("SportsEsports", Icons.Outlined.SportsEsports),
    IconItem("MusicNote", Icons.Outlined.MusicNote),
    IconItem("Celebration", Icons.Outlined.Celebration),
    IconItem("ConfirmationNumber", Icons.Outlined.ConfirmationNumber),

    // Education & Family
    IconItem("School", Icons.Outlined.School),
    IconItem("FamilyRestroom", Icons.Outlined.FamilyRestroom),
    IconItem("ChildFriendly", Icons.Outlined.ChildFriendly),
    IconItem("Pets", Icons.Outlined.Pets),

    // Other
    IconItem("AddCard", Icons.Outlined.AddCard),
    IconItem("Redeem", Icons.Outlined.Redeem), // Gift
    IconItem("DryCleaning", Icons.Outlined.DryCleaning),
    IconItem("Category", Icons.Outlined.Category)
)

fun getIconVector(name: String): ImageVector {
    return availableIcons.find { it.name == name }?.imageVector ?: Icons.Outlined.Category
}

@Composable
fun IconPickerDialog(
    onDismiss: () -> Unit,
    onIconSelected: (IconItem) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select an Icon") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(availableIcons) { iconItem ->
                    Icon(
                        imageVector = iconItem.imageVector,
                        contentDescription = iconItem.name,
                        modifier = Modifier.clickable { onIconSelected(iconItem) }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
