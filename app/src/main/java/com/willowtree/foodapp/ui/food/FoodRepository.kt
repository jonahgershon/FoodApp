package com.willowtree.foodapp.ui.food

import com.willowtree.foodapp.api.FoodishApi
import com.willowtree.foodapp.api.data.FoodishResponse

class FoodRepository (val foodishApi : FoodishApi){
    suspend fun getRandomFoodPic() : FoodishResponse{
        return foodishApi.getRandomFoodPic()
    }
    suspend fun getCategoryFoodPic() : FoodishResponse{
        return foodishApi.getCategoryFoodPic()
    }
}