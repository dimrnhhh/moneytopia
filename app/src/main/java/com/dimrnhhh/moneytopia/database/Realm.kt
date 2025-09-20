package com.dimrnhhh.moneytopia.database

import com.dimrnhhh.moneytopia.models.Category
import com.dimrnhhh.moneytopia.models.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

val config = RealmConfiguration.create(schema = setOf(Expense::class, Category::class))
val realm: Realm = Realm.open(config)

fun initializeCategories() {
    val categories = listOf(
        "Bills", "Debt", "Education", "Family", "Foods & Drinks",
        "Healthcare", "Savings", "Shopping", "Social Events",
        "Top Up", "Transportation", "Others"
    )

    val icons = listOf(
        "Payments", "CreditCard", "School", "Cottage", "Fastfood",
        "MonitorHeart", "Savings", "ShoppingBag", "Diversity1",
        "AddCard", "LocalTaxi", "Category"
    )

    realm.writeBlocking {
        if (query<Category>().find().isEmpty()) {
            categories.forEachIndexed { index, name ->
                copyToRealm(Category().apply {
                    this.name = name
                    this.icon = icons[index]
                })
            }
        }
    }
}
