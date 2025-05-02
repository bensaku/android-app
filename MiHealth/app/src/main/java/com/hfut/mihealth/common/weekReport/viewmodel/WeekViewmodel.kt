package com.hfut.mihealth.common.weekReport.viewmodel

import android.annotation.SuppressLint
import android.graphics.Insets.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.network.RecordService
import com.hfut.mihealth.network.client.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.time.ExperimentalTime

class WeekViewmodel : ViewModel() {
    // 私有的 MutableLiveData，用于数据的实际操作
    private val _weekData: MutableLiveData<Map<String, Map<String, Double>>> =
        MutableLiveData(emptyMap())

    // 公开的 LiveData，供外部观察使用
    val weekData: LiveData<Map<String, Map<String, Double>>> get() = _weekData

    private val _weekReport: MutableLiveData<String> = MutableLiveData("")
    val weekReport: LiveData<String> get() = _weekReport

    // 更新数据
    private fun updateWeekDate(newData: Map<String, Map<String, Double>>) {
        _weekData.postValue(newData)
    }

    private var date = getCurrentDate()

    init {
        date = getCurrentDate()
        getWeekDataRx()
        getWeekReport()
    }

    //上周的日期
    private fun getWeekData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    RetrofitClient.instance.create(RecordService::class.java).getWeekRecord(date)
                withContext(Dispatchers.Main) {
                    updateWeekDate(response)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getWeekDataRx(){
        viewModelScope.launch {
            RetrofitClient.instance.create(RecordService::class.java).getWeekRecord_Rx(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    updateWeekDate(response)
                }, { error ->
                    error.printStackTrace()
                })
        }
    }

    private fun getWeekReport() {
        viewModelScope.launch(Dispatchers.IO)  {
            pollWeekReport(date)
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun pollWeekReport(date: String, maxAttempts: Int = 15, delayBetweenAttempts: Long = 2000) {
        var attempt = 0
        while (attempt < maxAttempts) {
            attempt++
            val response = try {
                RetrofitClient.instance.create(RecordService::class.java).getWeekReport(date)
            } catch (e: Exception) {
                println("Attempt $attempt failed with exception: ${e.message}")
                delay(delayBetweenAttempts)
                continue
            }

            if (response.isCompleted) {
                // 处理成功的响应
                println("Successfully fetched week report: ${response.reportContent}")
                _weekReport.postValue(response.reportContent)
                break
            } else {
                println("Attempt $attempt failed with code: ${response.reportContent}")
            }

            if (attempt < maxAttempts) {
                delay(delayBetweenAttempts)
            }
        }

        if (attempt == maxAttempts) {
            println("Max attempts reached without success.")
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        // 从当前日期减去7天，得到上周的
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    @SuppressLint("NewApi")
    public fun getCalories(): List<Double> {
        // 获取原始数据
        val rawData = _weekData.value ?: return emptyList()

        // 定义日期格式
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // 假设日期格式为 "yyyy-MM-dd"

        // 转换并按日期降序排序
        val sortedData = rawData.entries.sortedBy { LocalDate.parse(it.key, formatter) }

        // 创建用于存储卡路里值的列表
        val calories = mutableListOf<Double>()

        // 按照排序后的顺序添加卡路里值
        for (item in sortedData) {
            item.value["calories"]?.let { calories.add(it) }
        }

        return calories
    }

    public fun getTakeValue(): Map<String, Double> {
        // 获取原始数据
        val rawData = _weekData.value ?: return emptyMap()

        // 初始化用于存储营养成分总量的 Map
        val takeValue = mutableMapOf<String, Double>().apply {
            put("carbs", 0.0)
            put("protein", 0.0)
            put("fat", 0.0)
        }

        // 遍历每个条目并累加 carbs、protein 和 fat 的值
        rawData.forEach { (_, value) ->
            takeValue["carbs"] = (takeValue["carbs"] ?: 0.0) + (value["carbs"] ?: 0.0)
            takeValue["protein"] = (takeValue["protein"] ?: 0.0) + (value["protein"] ?: 0.0)
            takeValue["fat"] = (takeValue["fat"] ?: 0.0) + (value["fat"] ?: 0.0)
        }

        return takeValue
    }

    override fun onCleared() {
        super.onCleared()
    }
}