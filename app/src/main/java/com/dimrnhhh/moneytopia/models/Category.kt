package com.dimrnhhh.moneytopia.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Cottage
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Diversity1
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.dimrnhhh.moneytopia.R

sealed class Category {
    data object Bills: Category()
    data object Debt: Category()
    data object Education: Category()
    data object Family: Category()
    data object FoodsAndDrinks: Category()
    data object Healthcare: Category()
    data object Savings: Category()
    data object Shopping: Category()
    data object SocialEvents: Category()
    data object TopUp: Category()
    data object Transportation: Category()
    data object Others: Category()
}

@Composable
fun Category.getName(): String {
    return when (this) {
        Category.Bills -> stringResource(R.string.bills)
        Category.Debt -> stringResource(R.string.debt)
        Category.Education -> stringResource(R.string.education)
        Category.Family -> stringResource(R.string.family)
        Category.FoodsAndDrinks -> stringResource(R.string.foods_and_drinks)
        Category.Healthcare -> stringResource(R.string.healthcare)
        Category.Savings -> stringResource(R.string.savings)
        Category.Shopping -> stringResource(R.string.shopping)
        Category.SocialEvents -> stringResource(R.string.social_events)
        Category.TopUp -> stringResource(R.string.top_up)
        Category.Transportation -> stringResource(R.string.transportation)
        Category.Others -> stringResource(R.string.others)
    }
}

@Composable
fun Category.getIcon(): ImageVector {
    return when (this) {
        Category.Bills -> Icons.Outlined.Payments
        Category.Debt -> Icons.Outlined.CreditCard
        Category.Education -> Icons.Outlined.School
        Category.Family -> Icons.Outlined.Cottage
        Category.FoodsAndDrinks -> Icons.Outlined.Fastfood
        Category.Healthcare -> Icons.Outlined.MonitorHeart
        Category.Savings -> Icons.Outlined.Savings
        Category.Shopping -> Icons.Outlined.ShoppingBag
        Category.SocialEvents -> Icons.Outlined.Diversity1
        Category.TopUp -> Icons.Outlined.AddCard
        Category.Transportation -> Icons.Outlined.LocalTaxi
        Category.Others -> Icons.Outlined.Category
    }
}