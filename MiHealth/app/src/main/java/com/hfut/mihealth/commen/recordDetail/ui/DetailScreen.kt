package com.hfut.mihealth.commen.recordDetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailScreen() {
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
                    .padding(
                        20.dp
                    )
            ) {
                DatePicker(state = datePickerState)
                OneDayRecordCard("11", "11", "11,", "11")
                MealRecord()
            }
        }
    }
}