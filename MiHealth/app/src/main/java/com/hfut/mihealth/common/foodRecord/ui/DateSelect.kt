package com.hfut.mihealth.common.foodRecord.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hfut.mihealth.common.foodRecord.viewmodel.FoodViewModel
import java.util.Date

@Preview
@Composable
fun DateSelectPreview() {
    //RecordDatePicker(onClose = {}, onDateSelected = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun DateSelect(onClose: () -> Unit) {
    val interactionSource = MutableInteractionSource()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = 0.5f))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onClose()
            },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(500.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ) // 白色背景，顶部圆角
                .padding(20.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) { }
        ) {
            val datePickerState = rememberDatePickerState()
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDatePicker(onClose: () -> Unit, viewModel: FoodViewModel) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                onClose()
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClose()
                        openDialog.value = false
                        val selectedDate = if (datePickerState.selectedDateMillis != null) {
                            Date(datePickerState.selectedDateMillis!!)
                        } else {
                            // 如果没有选择任何日期，则可以考虑使用当前时间或其他默认值
                            Date()
                        }
                        viewModel.updateDate(selectedDate)
                    },
                    enabled = confirmEnabled.value,
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                        openDialog.value = false
                    }
                ) {
                    Text("取消")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}