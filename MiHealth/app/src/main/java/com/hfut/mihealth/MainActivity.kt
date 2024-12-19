package com.hfut.mihealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Text("Hello world!")
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {
            NavigationDemo()
        }
    ) {
        // Main content of the screen
        Text("Hello world!", modifier = Modifier.padding(it))
    }
}

@Composable
@Preview
fun NavigationDemo() {
    val items = listOf("Item 1", "Item 2", "Item 3")
    NavigationBar {
        IconButton(onClick = { /* Handle home click */ }) {
            Icon(Icons.Default.Home, contentDescription = "Home")
        }
        Button(onClick = { /*TODO*/ }) {
            Text("拍照")
        }
        IconButton(onClick = { /* Handle favorite click */ }) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
        }
        IconButton(onClick = { /* Handle profile click */ }) {
            Icon(Icons.Default.Person, contentDescription = "Profile")
        }
    }
}


