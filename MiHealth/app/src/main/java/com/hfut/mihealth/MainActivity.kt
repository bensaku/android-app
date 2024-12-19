package com.hfut.mihealth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen() {
    val items = SnackbarHostState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) {
        AppContent("Hello world!")
    }
}

@Composable
fun AppContent(item: String) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(item)
    }
}


@Composable
fun BottomNavigationBar() {
    NavigationBar (
        //modifier =
    ){
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


