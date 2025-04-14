package com.hfut.mihealth.commen.foodRecord

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfut.mihealth.R
import com.hfut.mihealth.network.data.Food
import com.hfut.mihealth.commen.foodRecord.viewmodel.FoodCount
import com.hfut.mihealth.commen.foodRecord.ui.FoodSelect
import com.hfut.mihealth.commen.foodRecord.ui.RecordBottomArea
import com.hfut.mihealth.commen.foodRecord.viewmodel.FoodViewModel
import com.hfut.mihealth.commen.foodRecord.ui.RecordDatePicker
import com.hfut.mihealth.commen.foodRecord.ui.RecordFoodList
import com.hfut.mihealth.commen.foodRecord.ui.RecordTopArea
import com.hfut.mihealth.commen.foodRecord.ui.SelectedFoodItem

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
fun RecordScreen(viewModel: FoodViewModel = viewModel()) {
    val foodData by viewModel.foodData.collectAsState()
    val recordData by viewModel.foodCounts.collectAsState()
    val date by viewModel.date.collectAsState()
    var showOverlay by remember { mutableStateOf(false) }
    var showFood by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
    Scaffold(
        bottomBar = {
            if (!(showOverlay || showDate || showFood)) {
                RecordBottomArea(recordData, viewModel, onOpen = { showOverlay = true })
            }
        },
    ) { innerPadding ->
        Box() {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                RecordTopArea(onOpen = { showDate = true }, date, viewModel)
                RecordFoodList(foodData, onFoodItemClicked = {
                    showFood = true
                    selectedFood = it
                })
            }
            if (showFood) {
                FoodSelect(selectedFood, onClose = { showFood = false })
            }
            if (showOverlay) {
                RecordDetail(recordData, onClose = { showOverlay = false })
            }
            if (showDate) {
                RecordDatePicker(
                    onClose = { showDate = false },
                    viewModel
                )
            }
        }

    }
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RecordDetail(recordData: MutableList<FoodCount>, onClose: () -> Unit) {
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
            //todo 统计删除功能
            if (recordData.isEmpty()) {
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
                    items(recordData.size) { index ->
                        SelectedFoodItem(recordData[index])
                        if (index < recordData.size - 1) { // 不要在最后一个元素后添加分隔线
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