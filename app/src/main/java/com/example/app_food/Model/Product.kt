package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

class Product (
    @SerializedName("_id") val id: String,
    val name: String,
    val price: Number,
    val avatar: String,
    val infor: String,
    val category: String
)