package com.hfut.mihealth.common.recordDetail.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.network.RecordService
import com.hfut.mihealth.network.client.RetrofitClient
import com.hfut.mihealth.network.DTO.RecordAndImageResponse
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
class RecordViewModel : ViewModel() {
    private val _recordData = MutableStateFlow<RecordAndImageResponse>(
        RecordAndImageResponse(
            record = emptyMap(),
            image = emptyList()
        )
    )
    val recordData: StateFlow<RecordAndImageResponse> get() = _recordData

    private val _total = MutableStateFlow<total>(total(0, 0.0, 0.0, 0.0))
    val total: StateFlow<total> get() = _total


    private var date = getCurrentDate()
    private val _selectedDay = MutableStateFlow<CalendarDay?>(null)
    val selectedDay: StateFlow<CalendarDay?> get() = _selectedDay

    private val _currentMonth = MutableStateFlow<YearMonth>(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> get() = _currentMonth

    fun updateSelectedDay(day: CalendarDay) {
        _selectedDay.value = day
    }


    init {
        // 获取今天的日期
        val today = LocalDate.now()
        // 创建一个 CalendarDay 对象代表今天的日期
        val initialDay = CalendarDay(today, DayPosition.MonthDate)
        // 设置初始选中的日期
        _selectedDay.value = initialDay
        refresh()
    }

    fun resetState() {
        // 获取今天的日期
        val today = LocalDate.now()
        // 创建一个 CalendarDay 对象代表今天的日期
        val initialDay = CalendarDay(today, DayPosition.MonthDate)
        // 设置初始选中的日期
        _selectedDay.value = initialDay
        _currentMonth.value = YearMonth.now()
        date = getCurrentDate()
        refresh()
    }


    private fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.create(RecordService::class.java)
                    .getDietRecord(date)
                _recordData.value = response
                countTotal()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun updateDate(newDate: String) {
        date = newDate
        refresh()
    }

    private fun countTotal() {
        val records = _recordData.value.record!!.values.flatten()
        val newTotal = total(
            totalCalories = records.sumOf { it.calories },
            totalCarbohydrates = records.sumOf { it.carbs },
            totalFats = records.sumOf { it.fat },
            totalProteins = records.sumOf { it.protein }
        )
        _total.value = newTotal
    }
}

data class total(
    var totalCalories: Int,
    var totalCarbohydrates: Double,
    var totalFats: Double,
    var totalProteins: Double
)