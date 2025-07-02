package com.android.shoppinglist.data.repositories

import com.android.shoppinglist.data.db.ShoppingDatabase
import com.android.shoppinglist.data.db.entities.ShoppingItem

class ShoppingRepository(private val db: ShoppingDatabase) {
    suspend fun updateInsert(item: ShoppingItem) = db.getShoppingDao().updateInsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item);

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems();
}