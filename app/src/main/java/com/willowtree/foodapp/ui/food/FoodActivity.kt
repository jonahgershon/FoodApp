package com.willowtree.foodapp.ui.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.willowtree.foodapp.R
import com.willowtree.foodapp.api.FoodishApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(FoodishApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val foodishApi = retrofit.create(FoodishApi::class.java)

        GlobalScope.launch {
            val foodishResponse = foodishApi.getRandomFoodPic()
            Log.wtf("JG", "$foodishResponse")
        }
    }
}