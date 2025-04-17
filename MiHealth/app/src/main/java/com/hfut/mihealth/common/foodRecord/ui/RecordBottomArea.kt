package com.hfut.mihealth.common.foodRecord.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hfut.mihealth.R
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodCount
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodViewModel
import com.hfut.mihealth.common.recordDetail.viewmodel.total
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ThemeWhite

@Composable
fun RecordBottomArea(
    recordData: MutableList<FoodCount>,
    total: total,
    viewModel: FoodViewModel,
    onOpen: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val closeScreen by viewModel.closeScreenFlag.collectAsState()
    val context = LocalView.current.context as? Activity
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
                    tint = Color.Unspecified,
                    contentDescription = "food",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(30.dp)
                )
                if (recordData.size > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp) // 调整大小以适应计数
                            .clip(CircleShape)
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = recordData.size.toString(),
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
                    text = "共计 ${total.totalCalories} 千卡",
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
                showDialog = true
                viewModel.submitRecord()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Text(text = "保存记录")
        }
        LaunchedEffect(closeScreen) {
            if (closeScreen) {
                context?.onBackPressed()
            }
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = { /* 防止用户通过点击外部区域关闭对话框 */ }) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                color = ThemeWhite,
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 16.dp),
                        color = Green
                    )
                    Text(text = "】、正在上传...", fontSize = 16.sp)
                }
            }
        }
    }
}
