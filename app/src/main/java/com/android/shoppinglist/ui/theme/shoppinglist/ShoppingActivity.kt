package com.android.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.android.shoppinglist.data.db.ShoppingDatabase
import com.android.shoppinglist.data.repositories.ShoppingRepository
import com.android.shoppinglist.ui.theme.ShoppingListTheme
import com.android.shoppinglist.ui.theme.shoppinglist.ShoppingViewModel
import com.android.shoppinglist.ui.theme.shoppinglist.ShoppingViewModelFactory

class ShoppingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //initialise viewmodel dependencies
        val database = ShoppingDatabase(this);
        val repository = ShoppingRepository(database);
        val factory = ShoppingViewModelFactory(repository);

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ShoppingViewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java];

            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        Greeting("Android")
    }
}