package com.willowtree.foodapp.ui.food

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.willowtree.foodapp.R
import com.willowtree.foodapp.api.FoodishApi
import com.willowtree.foodapp.api.data.Category
import com.willowtree.foodapp.databinding.ActivityMainBinding
import com.willowtree.foodapp.ui.recipes.RecipeActivity
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class FoodActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var viewModelFactory: FoodViewModel.Factory
    val viewModel: FoodViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityMainBinding
    private var foodCategory: Category = Category.RANDOM
    private lateinit var foodItem: String

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
        setContentView(binding.root)
        viewModel.getRandomFoodPic()
        observeViewModel()

        binding.randomPicButton.setOnClickListener {
            viewModel.getRandomFoodPic(foodCategory)
            observeViewModel()
        }

        binding.getRecipesButton.setOnClickListener {
            val intent = RecipeActivity.newIntent(this, foodItem)
            startActivity(intent)
//            val url = "https://www.google.com/search?q=$foodItem+recipes"
//            val webIntent: Intent = Uri.parse(url).let { webpage ->
//                Intent(Intent.ACTION_VIEW, webpage)
//            }
//            startActivity(webIntent)
        }

        //Initialize Spinner
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
            foodItem = getCategoryFromUrl(foodPicUrl)
            binding.getRecipesButton.text = "GET $foodItem RECIPES"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        foodCategory = Category.RANDOM
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        foodCategory = Category.values()[position]
    }

    //Returns category from the Foodish API url response
    private fun getCategoryFromUrl(url: String): String {
        var start = url.indexOf("/", url.indexOf("images")) + 1
        var end = url.indexOf("/", start)
        return url.substring(start, end)
    }
}
