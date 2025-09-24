package com.dimrnhhh.moneytopia.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimrnhhh.moneytopia.models.Recurrence

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
    }

class ChartsViewModelFactory(
    private val application: Application,
    private val page: Int,
    private val recurrence: Recurrence
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChartsViewModel(application, page, recurrence) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}