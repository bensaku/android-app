package com.hfut.mihealth.ui.common.page

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.hfut.mihealth.R

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
        NoPermission(cameraPermissionState = cameraPermissionState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
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
            }) {
                Icon(
                    modifier = Modifier.clip(CircleShape),
                    painter = painterResource(id = R.drawable.mine_icon),
                    contentDescription = "拍照"
                )

            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (picBitmap.value != null) {
                Image(bitmap = picBitmap.value!!, contentDescription = "预览")
                Row {
                    Button(onClick = { picBitmap.value = null }) {
                        Text(text = "重拍")
                    }
                    Button(onClick = { picBitmap.value = null }) {
                        Text(text = "确认")
                    }
                }
            } else {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
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
                            scaleType = PreviewView.ScaleType.FIT_CENTER
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