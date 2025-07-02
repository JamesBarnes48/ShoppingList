package com.android.shoppinglist.ui.theme.shoppinglist

import androidx.lifecycle.ViewModel
import com.android.shoppinglist.data.db.entities.ShoppingItem
import com.android.shoppinglist.data.repositories.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository): ViewModel() {
    fun updateInsert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.updateInsert(item)
    }

    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    //this doesnt have to be in a coroutine because its only a read operation
    fun getAllShoppingItems() = repository.getAllShoppingItems()
}