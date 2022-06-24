package com.willowtree.foodapp.api.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodishResponse(val image : Sring)