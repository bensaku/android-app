package com.hfut.mihealth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchScreen()
        }
    }
}

@Composable
fun SearchScreen() {
    Image(
        painter = painterResource(id = R.drawable.search),
        contentDescription = "search",
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize()
    )

}


//搜索框
@Preview
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
//    val isSearchBarExpanded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Surface(
        color = Color(0xf8ffffff),
        shape = MaterialTheme.shapes.extraLarge,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                val intent = Intent(context, SearchActivity::class.java)
                context.startActivity(intent)
            }
    ) {
        Row {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("搜索食物") },
                enabled = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant // 设置图标的颜色
                    )
                },
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxHeight()
            )
            Text(
                text = "搜索",
                modifier = Modifier
                   .padding(end = 10.dp)
                   .clickable {
                        val intent = Intent(context, SearchActivity::class.java)
                        context.startActivity(intent)
                    }
            )
        }
    }
}
