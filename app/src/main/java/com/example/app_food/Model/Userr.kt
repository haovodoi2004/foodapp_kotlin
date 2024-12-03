package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

data class Userr (
    @SerializedName("_id") val id: String,
    val email:String,
    val password:String,
    val name:String,
    val address:String,
    val sex:Int
)