package com.willowtree.foodapp.ui.food

import com.willowtree.foodapp.api.FoodishApi
import com.willowtree.foodapp.api.data.Category
import com.willowtree.foodapp.api.data.FoodishResponse

class FoodRepository(private val foodishApi: FoodishApi) {

    suspend fun getRandomFoodPic(category: Category? = null): FoodishResponse {
        return if (category == Category.RANDOM || category == null) {
            foodishApi.getRandomFoodPic()
        } else {
            foodishApi.getRandomFoodPic(category.name.lowercase())
        }
    }
}
