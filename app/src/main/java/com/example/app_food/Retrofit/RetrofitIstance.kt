package com.example.app_food.Retrofit

import com.example.app_food.ApiServer.Apiserver
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.24.48.189:3000/")  // Đảm bảo base URL là chính xác
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Apiserver by lazy {
        retrofit.create(Apiserver::class.java)
    }
}
