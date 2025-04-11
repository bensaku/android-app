package com.hfut.mihealth.ui.common.food

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.FoodItem
import com.hfut.mihealth.RecordCard
import com.hfut.mihealth.SelectedFoodItem

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FoodSelect(onClose: () -> Unit) {
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
            FoodItem("mianbao")
        }

    }
}