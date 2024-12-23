package com.hfut.mihealth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomePageScreen() {
    var items = listOf("1", "2", "3")
    Column {
        FoodSearchBar()
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
    val isSearchBarExpanded = remember { mutableStateOf(false) }
    SearchBar(inputField = {
        Text(text = "search") },
        expanded = isSearchBarExpanded.value,
        onExpandedChange = {println("expanded")}
    ) {

    }
}

//饮食记录模块
@Composable
fun RecordCard() {

}

//食物卡片模块
@Preview
@Composable
fun FoodCard() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
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