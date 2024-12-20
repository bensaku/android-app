package com.hfut.mihealth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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
    var items = listOf("home","favorite","profile")
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar (
        modifier = Modifier.fillMaxWidth(1f)
    ){
        items.forEachIndexed { index, s ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(s) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}


