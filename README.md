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

@Database(
entities = [ShoppingItem::class],
version = 1
)
abstract class ShoppingDatabase: RoomDatabase()

This defines the room database itself. We supply our entities, which says our database is storing items of the ShoppingItem class.
Remember we previously annotated our ShoppingItem class with @Entity.

We have to increment the version every time we change the database schema.

We define our ShoppingDatabase class as an abstract class that extends RoomDatabase. This is what makes it a database.
Our database extends our Dao so it can do our insert, delete, query functions we defined there.

companion object {
    ...
}
This companion object is a singleton pattern - it means that we ensure only one instance of the database is created in the apps lifecycle.
Obvs you dont want two different databases instantiated!

Inside the companion object we have:

@Volatile
private var instance: ShoppingDatabase? = null;
private val LOCK = Any();

operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
    instance ?: createDatabase(context).also { instance = it }
}

private fun createDatabase(context: Context) =
    Room.databaseBuilder(context.applicationContext,
        ShoppingDatabase::class.java, "ShoppingDB.db").build();

@Volatile marks the instance of our database as volatile, ensuring visibility across threads (for coroutining)
We make a LOCK variable used to synchronize access to the singleton - only one thread can have the lock at once.

We use operator fun invoke to call ShoppingDatabase() passing in context as an argument to get the db instance.
If the instance exists then we return that, if not then we acquire the LOCK and instantiate it ourselves.