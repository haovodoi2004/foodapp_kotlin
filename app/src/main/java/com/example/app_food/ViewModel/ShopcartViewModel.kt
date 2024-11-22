package com.example.app_food.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Protype
import com.example.app_food.Model.Shopcart
import com.example.app_food.Model.User
import com.example.app_food.Repository.Repository
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ShopcartViewModel : ViewModel() {
    val datashopcart : MutableLiveData<Response<Shopcart>> = MutableLiveData()
    private val _ShopcartItems= mutableStateListOf<Shopcart>()
    val ShopcartItems = MutableLiveData<List<Shopcart>>()
    val updateResponse = MutableLiveData<Response<Shopcart>>()
    fun addshopcart(shopcart: Shopcart){
        viewModelScope.launch {
            try{
                val repone= RetrofitInstance.api.addshopcart(shopcart)
                datashopcart.value=repone
            }catch (e :Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchShopcart(){
        viewModelScope.launch {
            try{
                val respone=RetrofitInstance.api.getlistshopcart()
                println("Fetched data: $respone")
                Log.e("API Response"," Fetches data : $respone")
                _ShopcartItems.clear()  // Xóa dữ liệu cũ nếu cần
                _ShopcartItems.addAll(respone)
                ShopcartItems.postValue(_ShopcartItems)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateData(id: String, shopcart: Shopcart) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateData(id, shopcart)
                if (response.isSuccessful) {
                    updateResponse.postValue(response)
                } else {
                    Log.e("API Error", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API Error", "Exception: ${e.localizedMessage}")
            }
        }
    }
}