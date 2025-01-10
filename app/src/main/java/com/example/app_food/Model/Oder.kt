package com.example.app_food.Model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Oder (
    @SerializedName("_id") val id: String,
    val name:String,
    val phone:String,
    val id_pro:String,
    val quantity:Int,
    val all:Int,
    val address:String,
    val status:Int,
    val date:String,
    val emailuser:String
)