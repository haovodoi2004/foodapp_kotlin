package com.example.app_food.ApiServer

import com.example.app_food.Model.New
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.Model.Shopcart
import com.example.app_food.Model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Apiserver {

    data class RevenueResponse(
        val totalRevenue: Int
    )

    @GET("oder/revenue")
    fun getRevenue(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<RevenueResponse>

    @GET("protype/getlistprotype")
    suspend fun getprotype(): List<Protype>

    @GET("product/getbycategoryproduct/{category}")
    suspend fun getProbycategory(@Path("category") category:String): Response<List<Product>>

    @GET("new/getlistnew")
    suspend fun getnew(): List<New>

    @GET("product/getbyidproduct/{id}")
    suspend fun getProbyid(@Path("id") id:String) : Response<Product>

    @GET("shopcart/getlistshopcart")
    suspend fun getlistshopcart(): List<Shopcart>

    @GET("product/getlistproduct")
    suspend fun getproduct() : List<Product>

    @GET("us/getlistuser")
    suspend fun getlistuser() : List<User>

    @GET("oder/getlistoder")
    suspend fun getlistoder() : List<Oder>


    @POST("oder/addoder")
    suspend fun addoder(@Body oder:Oder) : Response<Oder>

    @POST("shopcart/addshopcart")
    suspend fun addshopcart(@Body shopcart: Shopcart) : Response<Shopcart>

    @POST("us/adduser")
    suspend fun adduser (@Body user:User) : Response<User>

    @POST("us/login")
    suspend fun login(@Body user: User): Response<User>

    @POST("protype/addprotype")
    suspend fun addprotype(@Body protype: Protype) : Response<Protype>

    @POST("product/addproduct")
    suspend fun addproductt(@Body product: Product) : Response<Product>



    @PUT("shopcart/editshopcart/{idpro}")
    suspend fun updateData(
        @Path("idpro") idpro: String,
        @Body shopcart: Shopcart
    ): Response<Shopcart>

    @PUT("product/editproduct/{idpro}")
    suspend fun updatePro(
        @Path("idpro") idpro:String,
        @Body product: Product
    ):Response<Product>

    @PUT("protype/editprotype/{name}")
    suspend fun updateprotype(@Path("name") name : String,@Body protype: Protype) : Response<Protype>

    @PUT("us/edituser/{id}")
    suspend fun updateuser(@Path("id") id : String , @Body user: User) : Response<User>

    @PUT("oder/editoder/{id}")
    suspend fun updateoder(@Path("id") id : String , @Body oder : Oder) : Response<Oder>


    @DELETE("shopcart/deleteshopcart/{idpro}")
    suspend fun deleteShopcart(@Path("idpro") idpro : String) : Response<Shopcart>

    @DELETE("protype/deleteprotype/{name}")
    suspend fun  deleteprotype(@Path("name") name : String) : Response<Protype>

    @DELETE("us/deleteuser/{id}")
    suspend fun deleteuser(@Path("id") email: String) : Response<User>

    @DELETE("product/deleteproduct/{id}")
    suspend fun deleteproduct(@Path("id") id : String) : Response<Product>


}