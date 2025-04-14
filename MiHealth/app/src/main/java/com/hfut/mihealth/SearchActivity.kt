package com.hfut.mihealth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Preview
@Composable
fun SearchScreen() {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            SearchBar()
            SearchHistory()
            SearchHint()
            //todo 判断后展示搜索结果
            SearchResult()
        }
    }

}


//搜索框
@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current as? Activity
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            painter = painterResource(R.drawable.left_arrow),
            contentDescription = "back",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    //todo 返回
                    context?.onBackPressed()
                }
        )
        Surface(
            color = colorResource(R.color.light_gray),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 5.dp,
            modifier = Modifier
                .padding(all = 8.dp)
                .width(250.dp)
                .height(40.dp)
        ) {
            var text by remember { mutableStateOf("") }
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.gray),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(40.dp)
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant // 设置图标的颜色
                )
                //BuildImageIcon(R.drawable.icon_search_black, 24.dp)
                val hint = "搜索食物"
                BasicTextField(
                    decorationBox = { innerTextField ->
                        if (text.isBlank() && hint.isNotEmpty())
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                innerTextField()
                                Text(
                                    hint ?: "",
                                    modifier = Modifier,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            } else innerTextField()
                    },
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                )
            }
        }
        Text(
            text = "搜索",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier
                .padding(start = 5.dp, end = 10.dp)
                .align(Alignment.CenterVertically)
                .clickable { })
    }
}

@Preview
@Composable
fun SearchHistory() {

    // 存储搜索历史记录
    var searchHistory by remember { mutableStateOf(mutableListOf<String>("aaa", "bbb", "ccc", "bbb", "ccc")) }
    // 存储当前输入的搜索内容
    var searchQuery by remember { mutableStateOf("aaa") }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "搜索历史",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            )
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = "delete",
                modifier = Modifier
                    .height(20.dp)
                    .padding(end = 10.dp)
            )
        }

        if (searchHistory.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp), // 设置最小宽度，达到胶囊效果
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // 控制水平间距
            ) {
                items(searchHistory.size) { index ->
                    val historyItem = searchHistory[index]
                    Text(
                        text = historyItem,
                        modifier = Modifier
                            .width(100.dp)
                            .clickable {
                                // 当点击历史记录时，可以重新设置搜索框内容
                                searchQuery = historyItem
                            }
                            .then(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)), // 添加内边距制造胶囊形状
                        style = MaterialTheme.typography.bodyMedium // 根据主题调整文本样式
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun SearchHint() {
    Row {
        Text(
            text = "猜你想搜",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun SearchResult() {

}