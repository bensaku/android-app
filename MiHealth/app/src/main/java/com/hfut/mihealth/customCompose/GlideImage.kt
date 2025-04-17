package com.hfut.mihealth.customCompose

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@Composable
fun GlideImage(url: String, modifier: Modifier) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
        ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(5)))
                .into(this)
        }
    }, update = { imageView ->
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    })
}

//@Composable
//fun GlideImage(url: String, modifier: Modifier = Modifier) {
//    AndroidView(
//        modifier = modifier,
//        factory = { context ->
//            ImageView(context).apply {
//                // 设置 scaleType 为 CENTER_CROP，使得图片填满 ImageView 并可能裁剪
//                scaleType = ImageView.ScaleType.CENTER_CROP
//                // 初始加载或设置默认图片
//                setImageResource(R.drawable.placeholder) // 设置一个默认占位图
//            }
//        },
//        update = { imageView ->
//            if (url.isNotEmpty()) {
//                Glide.with(imageView.context)
//                    .load(url)
//                    .apply(RequestOptions().centerCrop()) // 使用 Glide 的中心裁剪选项
//                    .into(imageView)
//            } else {
//                // 处理空URL的情况，可以设置一个错误图片或其他处理方式
//                imageView.setImageResource(R.drawable.error_image)
//            }
//        }
//    )
//}