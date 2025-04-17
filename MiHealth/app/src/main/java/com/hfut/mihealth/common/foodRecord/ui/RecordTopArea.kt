package com.hfut.mihealth.common.foodRecord.ui

import android.app.Activity
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.mihealth.R
import com.hfut.mihealth.common.camera.CameraActivity
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodViewModel
import com.hfut.mihealth.ui.theme.Green
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecordTopArea(onOpen: () -> Unit, recordDate: Date, viewModel: FoodViewModel) {
    // 顶部区域的内容
    // 返回 时间 三餐  拍照按钮
    val context = LocalView.current.context as? Activity

    // 格式化日期
    val formattedDate = remember(recordDate) {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(recordDate)
    }
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.left_arrow),
                contentDescription = "back",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable {
                        context?.onBackPressed()
                    }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "记饮食",
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onOpen() }
                ) {
                    Text(
                        text = formattedDate,
                    )
                    Icon(
                        painter = painterResource(R.drawable.triangle),
                        tint = androidx.compose.ui.graphics.Color.Unspecified,
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(12.dp)
                            .padding(start = 5.dp)
                            .rotate(180F)
                    )
                }
            }
            val context = LocalContext.current as? Activity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .height(30.dp)
                    .width(100.dp)
                    .background(
                        colorResource(id = R.color.gray),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        context?.startActivity(
                            android.content.Intent(
                                context,
                                CameraActivity::class.java
                            )
                        )
                    },
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

        Spacer(modifier = Modifier.height(10.dp))


        val meal = viewModel.meals.collectAsState().value
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
                        .clickable(
                            indication = null, // 禁用点击指示（即波纹效果）
                            interactionSource = remember { MutableInteractionSource() }, // 提供一个空的交互源
                        ) {
                            viewModel.updateMeals(tag)
                        }
                        .border(
                            width = 1.dp,
                            color = if (tag.equals(meal)) Green else Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            color = if (tag.equals(meal)) Green else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tag,
                        color = if (tag.equals(meal)) Color.White else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (tag.equals(meal)) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

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
        Spacer(modifier = Modifier.height(15.dp))
    }
}


@Composable
fun RecordSearchBar() {
    var query by remember { mutableStateOf("") }
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

