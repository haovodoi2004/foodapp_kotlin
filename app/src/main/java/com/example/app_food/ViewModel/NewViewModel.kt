package com.example.app_food.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.New
import com.example.app_food.Model.Product
import com.example.app_food.Model.Protype
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class NewViewModel:ViewModel() {
    val NewItems =MutableLiveData<List<New>>()
    private val _news = MutableLiveData<New>()
    val neww: LiveData<New> = _news

    fun getnewbyid(id : String){
        viewModelScope.launch {
            try {
                val respone=RetrofitInstance.api.getNewid(id)
                if(respone.isSuccessful){
                    _news.postValue(respone.body())
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun updatenew(id : String, new: New){
        viewModelScope.launch {
            val respone=RetrofitInstance.api.updatenew(id,new)
            if(respone.isSuccessful){
                _news.postValue(new)
                fetchNew()
            }
        }
    }

    fun addnew(new : New){
        viewModelScope.launch {
            val respone=RetrofitInstance.api.addnew(new)
            try {
                if (respone.isSuccessful){
                    fetchNew()
                }
            }catch (e:Exception){

            }
        }
    }

    fun newdelete(id : String){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.deletenew(id)
            try {
                if(respone.isSuccessful){
                    fetchNew()
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchNew(){
        viewModelScope.launch {
            try{
                val respone= RetrofitInstance.api.getnew()
                println("Fetched data: $respone")
                Log.e("API Response"," Fetches data : $respone")
                if(respone.isEmpty()){
                    NewItems.postValue(respone)
                }else{
                    NewItems.postValue(respone)
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}