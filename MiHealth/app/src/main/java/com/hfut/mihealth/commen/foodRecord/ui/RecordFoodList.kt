package com.hfut.mihealth.commen.foodRecord.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.commen.foodRecord.FoodItem
import com.hfut.mihealth.network.data.Food


@Composable
fun RecordFoodList(foodData: Map<String, List<Food>>, onFoodItemClicked: (Food) -> Unit) {

    // 安全初始化 selectedCategory
    var selectedCategory by remember {
        mutableStateOf(
            if (foodData.isNotEmpty()) foodData.keys.first() else "Default Category"
        )
    }
    Row(modifier = Modifier.fillMaxSize()) {
        // 左侧类别选择列表
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .width(90.dp)
                .border(1.dp, Color.LightGray)
        ) {
            items(foodData.size) { index ->
                val category = foodData.keys.elementAt(index)
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
                if (index < foodData.size - 1) { // 不要在最后一个元素后添加分隔线
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
            val items = foodData[selectedCategory]
            items?.let { it ->
                items(it.size) { index ->
                    FoodItem(it[index], onFoodItemClicked = { onFoodItemClicked(it) })
                    if (index < items.size - 1) { // 不要在最后一个元素后添加分隔线
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    }
                }
            }
        }
    }
}
