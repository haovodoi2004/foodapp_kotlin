package com.example.app_food.ApiServer

import com.example.app_food.Model.New
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.Model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Apiserver {
    @POST("us/adduser")
    suspend fun adduser (@Body user:User) : Response<User>

    @POST("us/login")
    suspend fun login(@Body user: User): Response<User>

    @GET("protype/getlistprotype")
    suspend fun getprotype(): List<Protype>

    @GET("product/getbycategoryproduct/{category}")
    suspend fun getProbycategory(@Path("category") category:String): Response<List<Product>>

    @GET("new/getlistnew")
    suspend fun getnew(): List<New>

    @GET("product/getbyidproduct/{id}")
    suspend fun getProbyid(@Path("id") id:String) : Response<Product>
}