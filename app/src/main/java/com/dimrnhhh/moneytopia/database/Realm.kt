package com.dimrnhhh.moneytopia.database

import com.dimrnhhh.moneytopia.models.Expense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

val config = RealmConfiguration.create(schema = setOf(Expense::class))
val realm: Realm = Realm.open(config)