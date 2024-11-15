package com.example.app_food.Model

<<<<<<< HEAD
class Product (
=======
import com.google.gson.annotations.SerializedName

class Product (
    @SerializedName("_id") val id: String,
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    val name: String,
    val price: Number,
    val avatar: String,
    val infor: String,
    val category: String
)