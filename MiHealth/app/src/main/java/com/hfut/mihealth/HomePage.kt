package com.hfut.mihealth

import androidx.camera.core.processing.SurfaceProcessorNode.In
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController

@Composable
fun HomePageScreen(navPos: NavHostController) {
    val items = listOf("1", "2", "3")
    Column {
        FoodSearchBar()
        RecordCard(navPos)
        WeekRecode()
        LazyColumn {
            items(items) { item ->
                FoodCard()
            }
        }
    }

}

//搜索框
@Preview
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
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon), // 使用导入的图标资源
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant // 设置图标的颜色
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(50.dp),
            )
        },
        expanded = isSearchBarExpanded.value,
        onExpandedChange = { println("expanded") }
    ) {
    }
}


//饮食记录模块
@Composable
fun RecordCard(navPos: NavHostController) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(280.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "饮食记录",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "查看详情",
                    modifier = Modifier
                        .padding(start = 10.dp),
                )
            }

            MealRecord()

            MealBar()
        }

    }
}

//摄入统计部分
@Composable
fun MealRecord() {
    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //todo 字体样式
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "今日已摄入",
            )

            Text(
                text = "114514千卡",
            )
        }
        Macronutrients()
        Macronutrients()
        Macronutrients()

    }
}

@Composable
fun Macronutrients() {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "脂肪"
        )
        Text(
            text = "进度条"
        )
        Text(
            text = "55克"
        )

    }
}

//三餐按钮部分
@Composable
fun MealBar() {
    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MealIcon("早餐", R.drawable.breakfast)

        MealIcon("午餐", R.drawable.launch)

        MealIcon("晚餐", R.drawable.dinner)

        MealIcon("加餐", R.drawable.dinner)
    }
}

@Composable
fun MealIcon(name: String, resource: Int) {
    Column(
        modifier = Modifier
            .clickable { }
            .padding(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Icon with click listener
        Icon(
            painter = painterResource(resource),
            contentDescription = "Favorite",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Text below the icon
        Text(text = name)
    }
}

//周报记录卡片
@Composable
fun WeekRecode() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(280.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Text(
            text = "周报"
        )

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