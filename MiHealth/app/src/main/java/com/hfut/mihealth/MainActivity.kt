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
import androidx.compose.material3.Surface
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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    val items = listOf("主页","拍照","我的")
    var selectedItem by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
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
    ) {
        paddingValues ->
        println(paddingValues)
        if (selectedItem == 0) {
            Text("首页")
        }else{
            Text("设置")
        }
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


