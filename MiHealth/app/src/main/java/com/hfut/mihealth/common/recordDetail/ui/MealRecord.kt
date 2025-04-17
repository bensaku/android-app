package com.hfut.mihealth.common.recordDetail.ui

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
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.R
import com.hfut.mihealth.customCompose.GlideImage
import com.hfut.mihealth.network.data.RecordResponse
import com.hfut.mihealth.ui.theme.Green

@Composable
fun MealRecord(key: String, value: List<RecordResponse>) {
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
            Text(text = key)

        }
        value.forEach(){ it ->
            RecordItem(it)
        }
    }
}

@Composable
fun RecordItem(foodCount: RecordResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧图片
        GlideImage(
            url = foodCount.imageurl,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)

        )

        Spacer(modifier = Modifier.width(16.dp)) // 图片与文字之间的间距

        // 中间两行文字
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = foodCount.name)
            Text(text = "约${foodCount.amount} 克", color = Color.Gray)
        }
        Text(
            text = "${foodCount.calories} 千卡",
        )
    }
}