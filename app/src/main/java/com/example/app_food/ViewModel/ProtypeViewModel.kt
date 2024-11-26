package com.example.app_food.ViewModel

import android.util.Log
import androidx.collection.mutableIntListOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Protype
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.await

class ProtypeViewModel:ViewModel() {
    private val _ProItems= mutableStateListOf<Protype>()
    val protypeItems = MutableLiveData<List<Protype>>()
    val dataprotype : MutableLiveData<Response<Protype>> = MutableLiveData()
    fun addprotype(protype: Protype){
        viewModelScope.launch {
            try {
                val respone=RetrofitInstance.api.addprotype(protype)
                dataprotype.value=respone
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchProtype(){
        viewModelScope.launch {
            try{
                val respone=RetrofitInstance.api.getprotype()
                println("Fetched data: $respone")
                Log.e("API Response"," Fetches data : $respone")
                _ProItems.clear()  // Xóa dữ liệu cũ nếu cần
                _ProItems.addAll(respone)
                protypeItems.postValue(_ProItems)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}