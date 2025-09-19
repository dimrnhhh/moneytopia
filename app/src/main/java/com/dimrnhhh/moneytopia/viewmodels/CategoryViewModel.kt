package com.dimrnhhh.moneytopia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimrnhhh.moneytopia.database.realm
import com.dimrnhhh.moneytopia.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import io.realm.kotlin.ext.query

data class CategoryState(
    val categories: List<Category> = listOf()
)

class CategoryViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CategoryState())
    val uiState: StateFlow<CategoryState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            realm.query<Category>().asFlow().collect {
                _uiState.value = CategoryState(categories = it.list)
            }
        }
    }

    fun addCategory(name: String, icon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.write {
                this.copyToRealm(
                    Category().apply {
                        this.name = name
                        this.icon = icon
                    }
                )
            }
        }
    }

    fun updateCategory(category: Category, name: String, icon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.write {
                val categoryToUpdate = this.query<Category>("_id == $0", category._id).find().first()
                categoryToUpdate.name = name
                categoryToUpdate.icon = icon
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.write {
                val categoryToDelete = this.query<Category>("_id == $0", category._id).find().first()
                delete(categoryToDelete)
            }
        }
    }
}
