package com.willowtree.foodapp.ui.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.willowtree.foodapp.R
import com.willowtree.foodapp.api.FoodishApi
import com.willowtree.foodapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FoodActivity : AppCompatActivity() {

    lateinit var viewModelFactory: FoodViewModel.Factory
    val viewModel: FoodViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityMainBinding


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(FoodishApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val foodishApi = retrofit.create(FoodishApi::class.java)

        val foodRepository = FoodRepository(foodishApi)
        viewModelFactory = FoodViewModel.Factory(foodRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.getRandomFoodPic()
        observeViewModel()

        binding.randomPicButton.setOnClickListener{
            viewModel.getRandomFoodPic()
            observeViewModel()
        }

        binding.samosaPicButton.setOnClickListener{
            viewModel.getCategoryFoodPic()
            observeViewModel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun observeViewModel() {
        viewModel.foodPicUrl.observe(this) { foodPicUrl ->
            Log.e("JG", "Observed foodPicUrl: $foodPicUrl")
            // For a simple view:
            Glide.with(this).load(foodPicUrl).into(binding.foodPic);
        }
    }
}
