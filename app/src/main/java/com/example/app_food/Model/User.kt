package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("_id") val id: String? = null, // `id` có thể null, không bắt buộc nhập
    val email:String,
    val password:String,
    val name:String,
    val address:String,
    val sex:Int,
    val status : Int
    )