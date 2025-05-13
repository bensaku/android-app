package com.hfut.mihealth.common.foodRecord.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.common.recordDetail.viewmodel.total
import com.hfut.mihealth.network.FoodService
import com.hfut.mihealth.network.client.RetrofitClient
import com.hfut.mihealth.network.DTO.Food
import com.hfut.mihealth.network.DTO.Record
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class FoodViewModel : ViewModel() {
    //时间
    private val _date = MutableStateFlow<Date>(Date()) // 使用当前时间初始化
    val date: StateFlow<Date> get() = _date

    //餐别
    private val _meals = MutableStateFlow<String>("")
    val meals: StateFlow<String> get() = _meals

    //食物选择列表的数据
    private val _foodData = MutableStateFlow<Map<String, List<Food>>>(emptyMap())
    val foodData: StateFlow<Map<String, List<Food>>> get() = _foodData

    //选中的食物数据
    private val _foodCounts = MutableStateFlow<MutableList<FoodCount>>(mutableListOf())
    val foodCounts: StateFlow<MutableList<FoodCount>> get() = _foodCounts

    private val _total = MutableStateFlow<total>(total(0, 0.0, 0.0, 0.0))
    val total: StateFlow<total> get() = _total

    private val _closeScreenFlag = MutableStateFlow(false)
    val closeScreenFlag: StateFlow<Boolean> get() = _closeScreenFlag

    private val disposable = CompositeDisposable()

    init {
        loadFoodData()
        //updateMealBasedOnTime()
    }

    private fun updateMealBasedOnTime() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val meal = when (currentHour) {
            in 6..10 -> "早餐"
            in 11..15 -> "午餐"
            in 17..20 -> "晚餐"
            else -> "加餐"
        }
        _meals.value = meal
    }

    private fun loadFoodData() {
        val foodService = RetrofitClient.getRetrofit().create(FoodService::class.java)

        viewModelScope.launch {
            foodService.getFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ foodMap ->
                    _foodData.value = foodMap // 更新 StateFlow
                }, { error ->
                    error.printStackTrace()
                    // Handle error appropriately
                }).let(disposable::add)
        }
    }


    fun addFoodCount(food: Food, count: Int) {
        val updatedList = _foodCounts.value.orEmpty().toMutableList()
        updatedList.add(FoodCount(food, count))
        _foodCounts.value = updatedList
    }

    fun deleteFoodCount(foodCount: FoodCount){
        val updatedList = _foodCounts.value.orEmpty().toMutableList()
        // 使用 removeIf 方法根据条件移除元素
        updatedList.removeIf { it.food == foodCount.food}
        // 或者，如果 FoodCount 类正确实现了 equals 和 hashCode 方法，可以直接使用：
        // updatedList.remove(foodCount)
        _foodCounts.value = updatedList
        countTotal()
    }


    fun updateOrAddFoodCount(food: Food, newCount: Int) {
        val updatedList = _foodCounts.value.orEmpty().toMutableList()
        // 查找对应的food项
        val index = updatedList.indexOfFirst { it.food == food }
        if (index != -1) { // 如果找到了对应的food项
            updatedList[index] = updatedList[index].copy(count = newCount)
            _foodCounts.value = updatedList
        } else {
            // 处理未找到的情况
            updatedList.add(FoodCount(food, newCount))
            _foodCounts.value = updatedList
        }
        countTotal()
    }

    private fun List<FoodCount>.orEmpty(): List<FoodCount> = this ?: mutableListOf()

    fun updateDate(newDate: Date) {
        _date.value = newDate
    }

    fun updateMeals(newMeals: String) {
        _meals.value = newMeals
    }

    private fun createRecord(date: Date, meals: String, foodCounts: List<FoodCount>): MutableList<Record> {
        val recordList = mutableListOf<Record>()
        foodCounts.forEach { foodCount ->
            val record = com.hfut.mihealth.network.DTO.Record(
                recordid = null,
                foodid = foodCount.food.foodid,
                userid = null,
                meals = meals,
                amount = foodCount.count,
                date = date
            )
            recordList.add(record)
        }
        return recordList
    }

    fun cleanData() {
        _foodCounts.value = mutableListOf()
    }

    fun submitRecord() {
        val recordService =
            RetrofitClient.getRetrofit().create(com.hfut.mihealth.network.RecordService::class.java)
        viewModelScope.launch {
            recordService.addRecord(createRecord(date.value, meals.value, foodCounts.value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(){
                    response ->
                    if (response) {
                        cleanData()
                        _closeScreenFlag.value = true
                    }
                }
        }
    }

    private fun countTotal() {
        val newTotal = total(
            totalCalories = foodCounts.value.sumOf { it.food.calories * it.count/100 },
            totalCarbohydrates = foodCounts.value.sumOf { it.food.carbs * it.count/100 },
            totalFats = foodCounts.value.sumOf { it.food.fat * it.count/100 },
            totalProteins = foodCounts.value.sumOf { it.food.protein * it.count/100 }
        )
        _total.value = newTotal
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear() // 清理订阅以避免内存泄漏
    }
}

data class FoodCount(val food: Food, val count: Int)
