package com.example.app_food.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Oder
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.Model.Shopcart
import com.example.app_food.Model.User
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ShopcartViewModel : ViewModel() {
    val datashopcart : MutableLiveData<Response<Shopcart>> = MutableLiveData()
    private val _ShopcartItems= mutableStateListOf<Shopcart>()
    val ShopcartItems = MutableLiveData<List<Shopcart>>()
    val updateResponse = MutableLiveData<Response<Shopcart>>()
    val product : MutableLiveData<Response<List<User>>> = MutableLiveData()


    fun addshopcart(shopcart: Shopcart){
        viewModelScope.launch {
            try{
                val repone= RetrofitInstance.api.addshopcart(shopcart)
                if(repone.isSuccessful){
                    val respone=RetrofitInstance.api.getlistshopcart()
                    ShopcartItems.postValue(respone)
                    fetchShopcart()
                }
            }catch (e :Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchShopcart(){
        viewModelScope.launch {
            try{
                val respone=RetrofitInstance.api.getlistshopcart()
                if(respone.isEmpty()){
                    ShopcartItems.postValue(respone)
                }else{
                    ShopcartItems.postValue(respone)
                }

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
                    fetchShopcart()
                } else {
                    Log.e("API Error", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API Error", "Exception: ${e.localizedMessage}")
            }
        }
    }

    fun deleteShopcart(id : String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteShopcart(id)
                if (response.isSuccessful) {
                    val updatedList = _ShopcartItems.filter { it.id != id }
                    _ShopcartItems.clear()
                    _ShopcartItems.addAll(updatedList)
                    _ShopcartItems.removeAll { it.id == id }
                    ShopcartItems.postValue(updatedList) // Cập nhật danh sách sau khi xóa
                    ShopcartItems.postValue(_ShopcartItems.toList())
                    fetchShopcart()
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