package com.hfut.mihealth.common.recordDetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.network.RecordService
import com.hfut.mihealth.network.client.RetrofitClient
import com.hfut.mihealth.network.data.RecordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RecordViewModel : ViewModel() {
    private val _recordData = MutableStateFlow<Map<String, List<RecordResponse>>>(emptyMap())
    val recordData: StateFlow<Map<String, List<RecordResponse>>> get() = _recordData

    private val _total = MutableStateFlow<total>(total(0, 0.0, 0.0, 0.0))
    val total: StateFlow<total> get() = _total


    private var date = getCurrentDate()

    init {
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
        val records = _recordData.value.values.flatten()
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