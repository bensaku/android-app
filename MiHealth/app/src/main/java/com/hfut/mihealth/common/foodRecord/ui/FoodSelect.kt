package com.hfut.mihealth.common.foodRecord.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodViewModel
import com.hfut.mihealth.network.DTO.Food
import com.hfut.mihealth.ui.theme.CarbsYellow
import com.hfut.mihealth.ui.theme.FatOrange
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ProteinGreen


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FoodSelect(selectedFood: Food?, onClose: () -> Unit, viewModel: FoodViewModel = viewModel()) {
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
        // 左侧的统计
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(400.dp)
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
            val sliderValue = remember { mutableStateOf(100f) }
            Text(
                text = "添加食物",
                fontSize = 20.sp,
            )
            if (selectedFood != null) {
                FoodItem(selectedFood, onFoodItemClicked = {})
                FoodInfoRow("碳水", selectedFood.carbs.toString(), CarbsYellow)
                FoodInfoRow("脂肪", selectedFood.fat.toString(), FatOrange)
                FoodInfoRow("蛋白质", selectedFood.protein.toString(), ProteinGreen)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(text = "卡路里 " + selectedFood.calories.toString())
                    Text(
                        text = "约"+"%.0f".format(sliderValue.value) + "克",
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )
                }
            }

            Slider(
                value = sliderValue.value/5, onValueChange = { newValue ->
                    sliderValue.value = newValue*5
                },
                valueRange = 0f..100f,
                steps = 10,
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 20.dp),
                onClick = {
                    viewModel.updateOrAddFoodCount(selectedFood!!, sliderValue.value.toInt())
                    onClose()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text(text = "保存记录")
            }
        }

    }
}

@Composable
fun FoodInfoRow(name: String, count: String, color: Color) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(15.dp) // 设置图标的大小
                    .background(
                        color = color, // 背景颜色
                        shape = RoundedCornerShape(5.dp) // 圆角半径
                    )
            ) {
            }
            Text(text = name)

        }
        Text(text = count + "克")
    }
}