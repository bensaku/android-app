package com.hfut.mihealth.common.camera.ui

import android.app.Activity
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.hfut.mihealth.R
import com.hfut.mihealth.ui.theme.Green
import com.hfut.mihealth.ui.theme.ThemeWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun CameraPageScreen() {
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    LaunchedEffect(key1 = Unit) {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        //接受拍照权限
        CameraContent()
    } else {
        //不接受
        CameraContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CameraContent() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }
    //请求关联context的主线程的Executor
    val cameraExecutor = ContextCompat.getMainExecutor(context)
    val picBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    val backgroundPainter: Painter = painterResource(R.drawable.background)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        print(innerPadding)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                CameraTopBar()
                Spacer(modifier = Modifier.height(15.dp))

                if (picBitmap.value != null) {
                    var showDialog by remember { mutableStateOf(false) }
                    Image(bitmap = picBitmap.value!!, contentDescription = "预览")
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Button(
                            onClick = { picBitmap.value = null },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = ThemeWhite,
                                containerColor = Green
                            )
                        ) {
                            Text(text = "重拍")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        val context = LocalContext.current as? Activity
                        val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
                        Button(
                            onClick = {
                                showDialog = true
                                // 模拟异步操作

                                coroutineScope.launch {
                                    delay(2000) // 模拟耗时操作
                                    picBitmap.value = null
                                    context?.onBackPressed()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = ThemeWhite,
                                containerColor = Green
                            )
                        ) {
                            Text(text = "确认")
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

                } else {
                    AndroidView(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(500.dp),
                        factory = { context ->
                            PreviewView(context).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                setBackgroundColor(Color.BLACK)
                                //设置渲染的实现模式
                                implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                                //设置缩放方式
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }.also {
                                it.controller = cameraController
                                cameraController.bindToLifecycle(lifecycleOwner)
                            }
                        },
                        onReset = {},
                        onRelease = {
                            cameraController.unbind()
                        }
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp),
                            onClick = {
                                cameraController.takePicture(
                                    cameraExecutor,
                                    object : ImageCapture.OnImageCapturedCallback() {
                                        override fun onCaptureSuccess(image: ImageProxy) {
                                            super.onCaptureSuccess(image)
                                            picBitmap.value = image.toBitmap().asImageBitmap()
                                        }

                                        override fun onError(exception: ImageCaptureException) {
                                            super.onError(exception)
                                        }
                                    })
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = ThemeWhite,
                                containerColor = Green
                            )
                        ) {
                            Icon(
                                modifier = Modifier.clip(CircleShape),
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = "拍照"
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoPermission(cameraPermissionState: PermissionState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val message = if (cameraPermissionState.status.shouldShowRationale) {
            "未获取照相机权限导致无法使用照相机功能"
        } else {
            "请授权照相机的权限"
        }
        Text(message)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            cameraPermissionState.launchPermissionRequest()
        }) {
            Text("请求授权")
        }
    }
}