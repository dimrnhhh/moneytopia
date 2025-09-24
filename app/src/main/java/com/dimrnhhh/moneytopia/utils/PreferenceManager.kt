package com.dimrnhhh.moneytopia.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("moneytopia_prefs", Context.MODE_PRIVATE)

    fun saveCurrencySymbol(symbol: String) {
        sharedPreferences.edit().putString("currency_symbol", symbol).apply()
    }

    fun getCurrencySymbol(): String {
        return sharedPreferences.getString("currency_symbol", "$") ?: "$"
    }
}
