# Shopping List for Android

This is a Kotlin application targeted for Android mobile users developed in Android Studio. It allows users to maintain a shopping list on their device,
adding and removing items as they see fit. The list is persistent, stored locally on their device using Room for SQLite database.

# Room and SQLite

SQL Server is used for big relational databases designed to store lots of data. Common in web apps like I have been developing. SQLite is more focused towards local
data storage within individual applications, which is why it is more prevalent in mobile development.

This project makes use of a library called Room. Its an official Android developer tool and is widely used for SQLite. The offical Android dev site says:

"The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite."

It is basically an API to interact with our SQLite DB in a clear and dev-friendly way. You'll see we mark things out throughout the app as a means of "configuring" or database.

# Some key Room syntax:

@Entity(tableName = "shopping_items")
data class ShoppingItem(...)

This tells room that this data class represents a table in our DB called shopping_items

@ColumnInfo(name = "item-name")
var name: String,

This tells Room to map this variable to a column called "item_name in the DB"

@PrimaryKey(autoGenerate = true)
var id: Int? = null;

This defines our primary key. It starts off as null when we make the object and then autoGenerate will automatically populate it when we make the class

@Dao
interface ShoppingDao

A DAO (Database Access Object) defines methods with which you can interact with the database. Its a bit like writing our your own ruleset for an API. postItem, updateItem etc.
Which of these methods you are allowed to do, it's like repository in controller-server-repository.

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun updateInsert(item: ShoppingItem)
@Delete
suspend fun delete(item: ShoppingItem)

Tell Room this function inserts/deletes a new item to/from the database, additionally on insert we specify that if the record already exists we replace the previous one.
'suspend' syntax says that this function is designed to run inside a coroutine, making it asynchronous

@Query("SELECT * FROM shopping_items")
fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

Lets you define a custom SQL query, run via that function.
We say it returns a LiveData object which is reactive, like ref() in vue!
We don't need to say 'suspend' but LiveData is async by nature!