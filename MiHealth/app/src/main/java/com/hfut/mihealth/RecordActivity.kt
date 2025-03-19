package com.hfut.mihealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RecordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecordScreen()
        }
    }
}

@Preview
@Composable
fun RecordScreen() {
    var showOverlay by remember { mutableStateOf(true) }
    Scaffold(
        bottomBar = {
            if (!showOverlay) {
                BottomArea()
            }
        },
    ) { innerPadding ->
        Box() {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                TopArea()
                FoodList()
            }


            if (showOverlay) {
                RecordList(onClose = { showOverlay = false })
            }
        }

    }
}

@Composable
fun TopArea() {
    // 顶部区域的内容
    // 返回 时间 三餐  拍照按钮
    val context = LocalView.current.context
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "back",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable {
                        (context as AppCompatActivity).finish()
                    }
            )
            Text(
                text = "3月7日 晚餐",
            )
            //todo 选择日期
            Row(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .height(30.dp)
                    .width(80.dp)
                    .background(
                        colorResource(id = R.color.gray),
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Text(
                    text = "拍照记录",
                )
                Icon(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = "camera",
                )
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentHeight()
        ) {
            //todo 搜索
            RecordSearchBar()
        }
    }

}

@Composable
fun RecordSearchBar() {
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Surface(
            color = colorResource(R.color.light_gray),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 5.dp,
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
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
                val hint = "搜索你想要的食物"
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
    }
}


@Composable
fun FoodList() {
    // 食物列表的内容
    // 类别 和类别下的详细食物， 点击食物弹出信息与添加按钮
    CategoryItemListScreen()
}

@Composable
fun CategoryItemListScreen() {
    // 假设这是你的数据模型
    val categories = listOf("主食", "蛋肉", "豆类", "蔬菜", "水果", "奶类", "饮品", "其他")
    val itemsByCategory = mapOf(
        "主食" to listOf("Apple", "Banana", "Orange"),
        "蛋肉" to listOf("Carrot", "Broccoli", "Spinach"),
        "豆类" to listOf("Milk", "Cheese", "Yogurt")
    )

    var selectedCategory by remember { mutableStateOf(categories.first()) }

    Row(modifier = Modifier.fillMaxSize()) {
        // 左侧类别选择列表
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
                .border(1.dp, Color.LightGray)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()

                        .clickable {
                            selectedCategory = category
                        }
                        .padding(16.dp),
                    color = if (category == selectedCategory) Color.Blue else Color.Black
                )
            }
        }

        // 右侧小项目列表
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp)
        ) {
            val items = itemsByCategory[selectedCategory]
            items?.let {
                items(it.size) { index ->
                    Text(
                        text = it[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomArea() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = "x项食物，共计114千卡",
        )
        // 完成按钮
        Button(
            onClick = {
                // 模拟点击完成按钮的操作
                // 在实际应用中，这里可以执行特定逻辑，比如保存选择或导航到其他页面
            },
            //enabled = selectedFoods.isNotEmpty(), // 只有当有选择时才启用按钮
            //modifier = Modifier.align(Alignment.End) // 按钮右对齐
        ) {
            Text(text = "完成")
        }
    }
}

@Composable
fun RecordList(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.5f))
            .clickable {
                onClose()
            },
    ) {
        // 假设这是用户已选择的食物列表
        val selectedFoods = listOf("苹果", "香蕉", "橙子")

        // 左侧的统计
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ) // 白色背景，顶部圆角
                .clickable { }
        ) {
            //todo 统计 一个概览卡片和每个小项目 下面是清空列表和保存按钮
            Text(
                text = "已选食物:",
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // 如果没有选择任何食物，则显示提示信息
            if (selectedFoods.isEmpty()) {
                Text(
                    text = "尚未选择任何食物",
                    color = Color.Gray
                )
            } else {
                // 遍历已选食物列表并显示
                selectedFoods.forEach { food ->
                    Text(
                        text = food,
                    )
                }
            }
        }
    }
}
