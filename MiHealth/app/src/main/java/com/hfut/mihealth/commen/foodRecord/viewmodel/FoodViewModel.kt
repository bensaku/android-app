package com.hfut.mihealth.commen.foodRecord.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfut.mihealth.network.FoodService
import com.hfut.mihealth.network.RetrofitClient
import com.hfut.mihealth.network.data.Food
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val _foodData = MutableStateFlow<Map<String, List<Food>>>(emptyMap())
    val foodData: StateFlow<Map<String, List<Food>>> get() = _foodData

    private val disposable = CompositeDisposable()

    init {
        loadFoodData()
    }

    private fun loadFoodData() {
        val foodService = RetrofitClient.instance.create(FoodService::class.java)

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

    private val _foodCounts = MutableStateFlow<MutableList<FoodCount>>(mutableListOf())
    val foodCounts: StateFlow<MutableList<FoodCount>> get() = _foodCounts

    fun addFoodCount(food: Food, count: Int) {
        val updatedList = _foodCounts.value.orEmpty().toMutableList()
        updatedList.add(FoodCount(food, count))
        _foodCounts.value = updatedList
    }


    fun updateOrAddFoodCount(food: Food, newCount: Int) {
        val updatedList = _foodCounts.value.orEmpty().toMutableList()
        // 查找对应的food项
        val index = updatedList.indexOfFirst { it.food == food }
        if (index != -1) { // 如果找到了对应的food项
            updatedList[index] = updatedList[index].copy(count = newCount)
            _foodCounts.value = updatedList
        } else {
            // 处理未找到的情况，比如抛出异常或者添加新的FoodCount到列表中
            // 这里仅作为示例，直接添加新项
            updatedList.add(FoodCount(food, newCount))
            _foodCounts.value = updatedList
        }
    }

    fun isFoodCountAdded(foodId: Int): Boolean {
        return _foodCounts.value.any { it.food.foodid == foodId }
    }

    private fun List<FoodCount>.orEmpty(): List<FoodCount> = this ?: mutableListOf()


    override fun onCleared() {
        super.onCleared()
        disposable.clear() // 清理订阅以避免内存泄漏
    }
}

data class FoodCount(val food: Food, val count: Int)
