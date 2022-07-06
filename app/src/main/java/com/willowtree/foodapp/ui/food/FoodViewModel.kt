package com.willowtree.foodapp.ui.food

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willowtree.foodapp.api.data.FoodishResponse
import kotlinx.coroutines.launch

class FoodViewModel(val foodRepository: FoodRepository) : ViewModel() {
    val foodUrl = MutableLiveData<FoodishResponse>()

    fun fetchRandomFood() {
        viewModelScope.launch { foodRepository.getRandomFoodPic() }
    }
}