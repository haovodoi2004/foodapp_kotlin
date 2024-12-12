package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

class New (
    @SerializedName("_id") val id: String,
    val title:String,
    val content:String,
    val avatar:String,

)