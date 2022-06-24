package com.willowtree.foodapp.api

import com.willowtree.foodapp.api.data.FoodishResponse
import retrofit2.http.GET

interface FoodishApi {

    companion object {
        val BASE_URL = "https://foodish-api.herokuapp.com/images/"
    }

    @GET("pizza")
    fun getRandomFoodPic() : FoodishResponse
}