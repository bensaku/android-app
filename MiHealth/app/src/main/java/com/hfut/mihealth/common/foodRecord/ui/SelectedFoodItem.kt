package com.hfut.mihealth.common.foodRecord.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodCount

@Composable
fun SelectedFoodItem(foodCount: FoodCount) {
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
            tint = androidx.compose.ui.graphics.Color.Unspecified,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable { } // 点击事件处理
                .size(24.dp)
        )
    }

}
