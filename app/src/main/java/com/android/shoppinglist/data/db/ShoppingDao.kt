package com.android.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.shoppinglist.data.db.entities.ShoppingItem

@Dao
interface ShoppingDao {
    //suspend lets us run these as coroutines - async!
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}