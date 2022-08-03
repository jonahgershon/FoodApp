package com.willowtree.foodapp.ui.food

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.willowtree.foodapp.R
import com.willowtree.foodapp.api.FoodishApi
import com.willowtree.foodapp.api.data.Category
import com.willowtree.foodapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var viewModelFactory: FoodViewModel.Factory
    val viewModel: FoodViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityMainBinding
    private var foodCategory : Category = Category.RANDOM

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

        binding.randomPicButton.setOnClickListener {
            viewModel.getRandomFoodPic(foodCategory)
            observeViewModel()
        }

        val spinner: Spinner = binding.foodCategorySpinner
        spinner.onItemSelectedListener = this

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.food_categories_array,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
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


    override fun onNothingSelected(parent: AdapterView<*>) {
        foodCategory = Category.RANDOM
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        foodCategory = Category.values()[position]
    }

}
