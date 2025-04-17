package com.hfut.mihealth.common.recordDetail.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.mihealth.R
import com.hfut.mihealth.ui.theme.ThemeWhite

@Composable
@Preview
fun RecordTopBar() {
    val context = LocalContext.current as? Activity
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.left_arrow),
            contentDescription = "back",
            modifier = Modifier
                .clickable {
                    // 返回操作
                    context?.onBackPressed()
                }
                .padding(start = 16.dp) // 根据需要调整内边距
        )
        Spacer(modifier = Modifier.weight(1f)) // 这个 Spacer 将会填充剩余的空间
        Text(
            text = "历史记录",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(end = 16.dp) // 可选：根据需要添加右边距
        )
        Spacer(modifier = Modifier.weight(1f)) // 另一个 Spacer 来确保 Text 居中
    }
}