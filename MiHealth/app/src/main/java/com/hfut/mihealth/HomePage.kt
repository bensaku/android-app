package com.hfut.mihealth

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ThemeWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(navPos: NavHostController) {
    val items = listOf("1", "2", "3")
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        FoodSearchBar()
        RecordCard(navPos)
        WeekRecord()
//        LazyColumn {
//            items(items) { item ->
//                FoodCard()
//            }
//        }
    }

}

//搜索框
@Preview
@Composable
fun FoodSearchBar() {
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
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}


//饮食记录模块
@Composable
fun RecordCard(navPos: NavHostController) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        color = ThemeWhite,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(250.dp)
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
@Preview
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
                text = "今日还可摄入",
            )

            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "1100",
                    modifier = Modifier
                       .padding(end = 5.dp),
                    fontSize = 20.sp,
                    color = Green
                )
                Text(
                    text = "千卡",
                )
            }
        }
        Macronutrients("脂肪")
        Macronutrients("碳水")
        Macronutrients("蛋白质")

    }
}

@Composable
fun Macronutrients(str: String) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = str
        )
        LinearProgressIndicator(
            progress = { 0.5f },
            color = Green,
            strokeCap = StrokeCap.Butt,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .width(50.dp),
        )
        Text(
            text = "55/60克"
        )

    }
}

//三餐按钮部分
@Composable
fun MealBar() {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MealIcon("早餐", R.drawable.breakfast)

        MealIcon("午餐", R.drawable.launch)

        MealIcon("晚餐", R.drawable.dinner)

        MealIcon("加餐", R.drawable.jiacan)
    }
}

@Composable
fun MealIcon(name: String, resource: Int) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clickable { }
            .padding(8.dp)
            .clickable {
                context.startActivity(Intent(context, RecordActivity::class.java))
            },
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Icon with click listener
        Icon(
            painter = painterResource(resource),
            tint = androidx.compose.ui.graphics.Color.Unspecified,
            contentDescription = "Favorite",
            modifier = Modifier.padding(bottom = 4.dp)
                .width(36.dp)
                .height(33.dp)
        )
        // Text below the icon
        Text(text = name)
    }
}

//周报记录卡片
@Composable
@Preview
fun WeekRecord() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Surface(
        color = Color(0x86ffffff),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(350.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Image(
            painter = painterResource(R.drawable.zhoubao),
            contentDescription = "周报表",
            modifier = Modifier
                .fillMaxWidth()
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
        color = Color(0x86ffffff),
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