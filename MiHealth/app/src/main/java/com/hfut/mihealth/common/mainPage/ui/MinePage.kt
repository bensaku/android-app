import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfut.mihealth.R
import com.hfut.mihealth.common.login.LoginActivity
import com.hfut.mihealth.common.mainPage.viewmodel.MainViewModel
import com.hfut.mihealth.network.client.RetrofitClient

@Composable
fun MinePageScreen(viewModel: MainViewModel) {
    var showIpDialog by remember { mutableStateOf(false) }
    val name by viewModel.userName.collectAsState()
    if (showIpDialog) {
        ChangeIpDialog(onDismiss = { showIpDialog = false }) { newIp ->
            // 在这里处理新的IP地址
            println("New IP: $newIp")
            RetrofitClient.updateIpAddress(newIp)
        }
    }
    Column {
        Spacer(modifier = Modifier.height(16.dp)) // 空隙
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // 空隙
            // 头像和用户名部分
            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(3f)
                        .clickable(
                            indication = null, // 禁用点击指示（即波纹效果）
                            interactionSource = remember { MutableInteractionSource() }, // 提供一个空的交互源
                        ) {
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        }, // 白色背景
                ) {
                    // 用户名
                    if (name != null) {
                        Image(
                            painter = painterResource(id = R.drawable.kirby), // 假设这是头像资源ID
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape) // 圆形裁剪以获得圆形头像效果
                        )
                        Spacer(modifier = Modifier.width(26.dp)) // 空隙
                        Text(text = name!!, fontSize = 24.sp, modifier = Modifier.weight(1f))
                    } else {
                        // 头像框
                        Image(
                            painter = painterResource(id = R.drawable.avatar), // 假设这是头像资源ID
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape) // 圆形裁剪以获得圆形头像效果
                        )
                        Spacer(modifier = Modifier.width(26.dp)) // 空隙
                        Text(text = "游客323423", fontSize = 24.sp, modifier = Modifier.weight(1f))
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.setting),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(30.dp)
                        .weight(1f)
                        .clickable(
                            indication = null, // 禁用点击指示（即波纹效果）
                            interactionSource = remember { MutableInteractionSource() }, // 提供一个空的交互源
                        ) {
                            showIpDialog = true
                        }
                )

            }
            Spacer(modifier = Modifier.height(16.dp)) // 空隙
            // 下方的图片，保持长宽比
            Image(
                painter = painterResource(R.drawable.mine), // 原始图片资源
                contentDescription = null,
                contentScale = ContentScale.FillWidth, // 根据宽度填充，保持比例
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp)) // 空隙
        }
    }
}

@Composable
fun ChangeIpDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var ipAddress by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Change IP Address") },
        text = {
            Column {
                TextField(
                    value = ipAddress,
                    onValueChange = { ipAddress = it },
                    label = { Text("Enter new IP address") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(ipAddress.text)
                    onDismiss()
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}