package com.willowtree.foodapp.ui.food

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.willowtree.foodapp.api.data.FoodishResponse
import kotlinx.coroutines.launch

class FoodViewModel(val foodRepository: FoodRepository) : ViewModel() {
    val foodPicUrl = MutableLiveData<String>()

    fun getRandomFoodPic() {
        Log.e("JG", "getRandomFoodPic called: ViewModel instance: $this")
        viewModelScope.launch {
            val foodishResponse = foodRepository.getRandomFoodPic()
            foodPicUrl.value = foodishResponse.image
        }
    }

    fun getCategoryFoodPic() {
        viewModelScope.launch {
            val foodishResponse = foodRepository.getCategoryFoodPic()
            foodPicUrl.value = foodishResponse.image
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val foodRepository: FoodRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FoodViewModel(foodRepository) as T
        }
    }
}