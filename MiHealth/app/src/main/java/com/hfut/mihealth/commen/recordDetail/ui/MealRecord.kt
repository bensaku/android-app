package com.hfut.mihealth.commen.recordDetail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.R
import com.hfut.mihealth.commen.foodRecord.viewmodel.FoodCount
import com.hfut.mihealth.network.data.Food
import com.hfut.mihealth.ui.theme.Green

@Composable
@Preview
fun MealRecord() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(15.dp)
                    .width(5.dp) // 设置图标的大小
                    .background(
                        color = Green, // 背景颜色
                        shape = RoundedCornerShape(5.dp) // 圆角半径
                    )
            ) {
            }
            Text(text = "早餐")

        }
        val foodCounts = listOf(
            FoodCount(
                food = Food(
                    foodid = 1,
                    name = "食物1",
                    calories = 100,
                    fat = 10.1,
                    protein = 10.1,
                    carbs = 10.2,
                    foodtype = "主食",
                    othernutritionalinfo = "主食",
                    imageurl = "主食",
                ),
                count = 100
            )
        )
        RecordItem(foodCounts[0])
    }
}

@Composable
fun RecordItem(foodCount: FoodCount) {
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
            Text(text = foodCount.food.name)
            Text(text = "${foodCount.food.calories} 千卡/100克", color = Color.Gray)
        }
        Text(text = "114千卡")
        // 右侧添加图标
        Icon(
            painter = painterResource(R.drawable.change), // 请确保有相应的资源文件
            contentDescription = "Add",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable { } // 点击事件处理
                .size(24.dp)
        )
    }

}