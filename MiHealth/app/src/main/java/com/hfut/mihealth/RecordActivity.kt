package com.hfut.mihealth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.mihealth.network.fetchFoodData
import com.hfut.mihealth.ui.common.food.FoodSelect
import com.hfut.mihealth.ui.theme.Green

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
    var showOverlay by remember { mutableStateOf(false) }
    var showFood by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            if (!showOverlay) {
                BottomArea(onOpen = { showOverlay = true })
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
            if (showFood) {
                FoodSelect(onClose = { showFood = false })
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "back",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable {
                        //todo 点击返回
                        (context as AppCompatActivity).onBackPressed()
                    }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "记饮食",
                )
                Text(
                    text = "4月10日 星期日",
                )
            }
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
                    text = "AI帮你记",
                )
                Icon(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = "camera",
                )
            }

        }
        val selectedIndex = remember { mutableStateOf(0) }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 定义标签文本数组
            val tags = listOf("早餐", "午餐", "晚餐", "加餐")

            // 循环创建标签
            tags.forEachIndexed { index, tag ->
                Box(
                    modifier = Modifier
                        .clickable {
                            selectedIndex.value = index
                        }
                        .border(
                            width = 1.dp,
                            color = if (selectedIndex.value == index) Green else Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = if (selectedIndex.value == index) Green else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag,
                        color = if (selectedIndex.value == index) Color.White else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (selectedIndex.value == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
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
    Surface(
        color = Color.Transparent,
        shape = MaterialTheme.shapes.extraLarge,
        tonalElevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, Color.LightGray, shape = MaterialTheme.shapes.extraLarge)
    ) {
        var text by remember { mutableStateOf("") }
        Row(
            Modifier
                .fillMaxWidth()
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
                .width(90.dp)
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
                if (index < categories.size - 1) { // 不要在最后一个元素后添加分隔线
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                }
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
                    FoodItem(it[index])
                    if (index < items.size - 1) { // 不要在最后一个元素后添加分隔线
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomArea(onOpen: () -> Unit) {
    var count = 5
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 居中对齐
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onOpen()
                },
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    painter = painterResource(R.drawable.breakfast),
                    tint = androidx.compose.ui.graphics.Color.Unspecified,
                    contentDescription = "food",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(30.dp)
                )
                //todo 数量显示
                if (count > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp) // 调整大小以适应计数
                            .clip(CircleShape)
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = count.toString(),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        )
                    }

                }
            }
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "早餐 ",
                )
                Text(
                    text = "共计114千卡",
                )
            }
            Icon(
                painter = painterResource(R.drawable.triangle),
                tint = androidx.compose.ui.graphics.Color.Unspecified,
                contentDescription = "icon",
                modifier = Modifier
                    .size(12.dp)
            )
        }
        // 完成按钮
        Button(
            modifier = Modifier
                .width(140.dp)
                .padding(end = 20.dp),
            onClick = {
                // 模拟点击完成按钮的操作
                // 在实际应用中，这里可以执行特定逻辑，比如保存选择或导航到其他页面
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Text(text = "保存记录")
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RecordList(onClose: () -> Unit) {
    val interactionSource = MutableInteractionSource()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.5f))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onClose()
            },
    ) {
        // 假设这是用户已选择的食物列表
        val selectedFoods = listOf("苹果", "香蕉", "橙子", "橙子", "橙子", "橙子")

        // 左侧的统计
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(450.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ) // 白色背景，顶部圆角
                .padding(20.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { }
        ) {
            //todo 统计 一个概览卡片和每个小项目 下面是清空列表和保存按钮
            if (selectedFoods.isEmpty()) {
                Text(
                    text = "尚未选择任何食物",
                    color = Color.Gray
                )
            } else {
                RecordCard("11", "11", "11,", "11")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // 可选：设置背景颜色
                ) {
                    items(selectedFoods.size) { index ->
                        SelectedFoodItem(selectedFoods[index])
                        if (index < selectedFoods.size - 1) { // 不要在最后一个元素后添加分隔线
                            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecordCard(calories: String, fat: String, protein: String, carbs: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 第一行：千卡
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "本次记录",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = calories + "千卡",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 第二行：营养成分
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = "脂肪",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = fat,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Row {
                    Text(
                        text = "蛋白质",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = protein,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Row {
                    Text(
                        text = "碳水",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = carbs,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun FoodItem(str: String) {
    //todo 食物项目的内容
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { fetchFoodData() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧图片
        Image(
            //todo 改成图片链接
            painter = painterResource(R.drawable.launch),
            contentDescription = "Food Image",
            modifier = Modifier
                .size(50.dp)
        )

        Spacer(modifier = Modifier.width(16.dp)) // 图片与文字之间的间距

        // 中间两行文字
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = str)
            Text(text = "$str 千卡/100克", color = Color.Gray)
        }

        // 右侧添加图标
        Icon(
            painter = painterResource(R.drawable.addicon), // 请确保有相应的资源文件
            contentDescription = "Add",
            tint = androidx.compose.ui.graphics.Color.Unspecified,
            modifier = Modifier
                .clickable { } // 点击事件处理
                .size(24.dp)
        )
    }
}

@Composable
fun SelectedFoodItem(food: String) {
    //todo 已选食物项目的内容
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧图片
        Image(
            //todo 改成图片链接
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Food Image",
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp)) // 图片与文字之间的间距

        // 中间两行文字
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = food)
            Text(text = "$food 千卡/100克", color = Color.Gray)
        }
        Text(text = "114千卡")
        // 右侧添加图标
        Icon(
            painter = painterResource(R.drawable.change), // 请确保有相应的资源文件
            contentDescription = "Add",
            tint = androidx.compose.ui.graphics.Color.Unspecified,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable { } // 点击事件处理
                .size(24.dp)
        )
    }

}
