package com.example.app_food.Model

import com.google.gson.annotations.SerializedName

class Shopcart (
    @SerializedName("_id") val id: String,
    val idpro:String,
    val quantity:Number,
    val all :Number,
    val emailuser:String,
    val status :Number
)