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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hfut.mihealth.R
import com.hfut.mihealth.common.recordDetail.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen(viewModel: RecordViewModel = viewModel()) {
    val recordData by viewModel.recordData.collectAsState()
    val total by viewModel.total.collectAsState()
    val backgroundPainter: Painter = painterResource(R.drawable.background)
    val datePickerState = rememberDatePickerState()

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
                RecordDate(viewModel)
                //DatePicker(state = datePickerState)
                OneDayRecordCard(
                    total.totalCalories.toString(),
                    total.totalFats.toString(),
                    total.totalProteins.toString(),
                    total.totalCarbohydrates.toString()
                )
//                recordData.forEach() { it ->
//                    MealRecord(it.key, it.value)
//                }
                LazyColumn {
                    items(recordData.size) { index ->
                        val (mealType, records) = recordData.toList().elementAt(index)
                        MealRecord(mealType, records)
                    }
                }
            }
        }
    }
}