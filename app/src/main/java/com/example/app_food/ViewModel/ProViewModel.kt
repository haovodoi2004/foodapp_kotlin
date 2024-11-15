package com.example.app_food.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Product
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ProViewModel:ViewModel() {
    val proo : MutableLiveData<Response<List<Product>>> = MutableLiveData()

    fun getProBycategoryy(category : String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.getProbycategory(category)
            proo.value = response
        }
    }
}