package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("_id") val id: String,
//    @SerializedName("_id") val id: String? = null, // Giá trị mặc định là null
    val name: String,
    val price: Number,
    val avatar: String,
    val infor: String,
    val category: String,
    val quantity:Number,
    val status:Number
)