package com.hfut.mihealth.common.recordDetail.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfut.mihealth.R
import com.hfut.mihealth.common.recordDetail.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen() {

    val backgroundPainter: Painter = painterResource(R.drawable.background)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        },
    ) { innerPadding ->
        print(innerPadding)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
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
                    .padding(innerPadding)
            ) {
                RecordTopBar()
                RecordArea()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordArea(viewModel: RecordViewModel = viewModel()) {
    val recordData by viewModel.recordData.collectAsState()
    val total by viewModel.total.collectAsState()
    // 或者更精确地监听生命周期事件
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // 当页面可见时执行的操作
            viewModel.resetState()
        }
    }
    Column {
        RecordDate(viewModel)
        OneDayRecordCard(
            total.totalCalories.toString(),
            "%.2f".format(total.totalFats),
            "%.2f".format(total.totalProteins),
            "%.2f".format(total.totalCarbohydrates)
        )
        LazyColumn {
            recordData.record?.let {
                items(it.size) { index ->
                    val (mealType, records) = recordData.record!!.toList().elementAt(index)
                    MealRecord(mealType, records)
                }
            }
            recordData.image?.let {
                if (recordData.image!!.size > 0){
                    item {
                        AIRecord(recordData.image!!)
                    }
                }
            }
        }
    }
}