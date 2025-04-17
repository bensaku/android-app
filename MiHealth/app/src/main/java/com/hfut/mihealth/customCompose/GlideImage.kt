package com.hfut.mihealth.customCompose

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

@Composable
fun GlideImage(url: String, modifier: Modifier) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
        ImageView(context).apply {
            Glide.with(context)
                .load(url)
                .into(this)
        }
    }, update = { imageView ->
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    })
}