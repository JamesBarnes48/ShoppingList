package com.android.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data classes are used to hold common functionality for data
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @ColumnInfo(name = "item-name")
    var name: String,
    @ColumnInfo(name = "item_amount")
    var amount: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null;
}