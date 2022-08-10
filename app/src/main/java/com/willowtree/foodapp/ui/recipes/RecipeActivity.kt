package com.willowtree.foodapp.ui.recipes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.willowtree.foodapp.databinding.ActivityRecipeBinding
import com.willowtree.foodapp.ui.food.FoodActivity


class RecipeActivity : AppCompatActivity() {
    companion object {
        private const val KEY_CATEGORY = "category"

        fun newIntent(context: Context, category: String) =
            Intent(context, RecipeActivity::class.java).apply { putExtra(KEY_CATEGORY, category) }
    }

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = intent.getSerializableExtra(KEY_CATEGORY)

        //Webview setup
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl("https://www.google.com/search?q=$category+recipes")

        val webViewClient = WebViewClientImpl(this)
        binding.webview.webViewClient = webViewClient

        //Enable back button on action bar
        this.actionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            return false
        }
    }

    //Override the back button on the application bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myIntent = Intent(applicationContext, FoodActivity::class.java)
        startActivityForResult(myIntent, 0)
        return true    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && this.binding.webview.canGoBack()) {
//            this.binding.webview.goBack()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binding.webview.canGoBack()) {
                        binding.webview.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}