package com.hfut.mihealth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun HomePageScreen() {
    var items = listOf("1", "2", "3")
    Column {
        FoodSearchBar()
        RecordCard()
        LazyColumn {
            items(items) { item ->
                FoodCard()
            }
        }
    }

}

//搜索框
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchBar() {
    var query by remember { mutableStateOf("") }
    val isSearchBarExpanded = remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.padding(8.dp),
        inputField = {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("搜索食物") },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(50.dp),
        )
                     },
        expanded = isSearchBarExpanded.value,
        onExpandedChange = {println("expanded")}
    ) {

    }
}

//饮食记录模块
@Composable
fun RecordCard() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Column {
            Row {
                Text(text = "今日已经摄入")
                Text(text = "碳水蛋白脂肪。。。")
            }
            Row {
                Text(text = "早餐午餐中餐")
                
            }
        }
        
    }

}

//食物卡片模块
@Composable
fun FoodCard() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) Color(0xFFCCCCCC) else MaterialTheme.colorScheme.surface,
        label = ""
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        color = surfaceColor
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "food picture",
                modifier = if (!isExpanded) Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .animateContentSize() else Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .animateContentSize()
            )
            Spacer(Modifier.padding(horizontal = 8.dp))
            Column {
                Text(text = "食物名称")
                Spacer(Modifier.padding(vertical = 8.dp))
                Text(
                    text = "介绍介绍介绍介绍介绍介绍介绍介绍绍介绍介绍介绍介绍介绍介绍绍介绍介绍介绍介绍介绍介绍绍介绍介绍介绍介绍介绍介绍绍介绍介绍介绍介绍介绍介绍",
                    // 修改 maxLines 参数，在默认情况下，只显示一行文本内容
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // Composable 大小的动画效果
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}