package com.willowtree.foodapp.api

import com.willowtree.foodapp.api.data.Category
import com.willowtree.foodapp.api.data.FoodishResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodishApi {

    companion object {
        const val BASE_URL = "https://foodish-api.herokuapp.com/api/"
    }

    @GET(".")
    suspend fun getRandomFoodPic() : FoodishResponse

    @GET("images/{category}")
    suspend fun getRandomFoodPic(@Path("category") category: String) : FoodishResponse
}