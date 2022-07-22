package com.willowtree.foodapp.api

import com.willowtree.foodapp.api.data.FoodishResponse
import retrofit2.http.GET

interface FoodishApi {

    companion object {
        const val BASE_URL = "https://foodish-api.herokuapp.com/api/images/"
    }

    @GET("pizza")
    suspend fun getRandomFoodPic() : FoodishResponse

    @GET("samosa")
    suspend fun getCategoryFoodPic() : FoodishResponse
}