package com.example.app_food.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Oder
import com.example.app_food.Model.User
import com.example.app_food.Repository.Repository
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class OderViewModel() : ViewModel(){
    val dataoder : MutableLiveData<Response<Oder>> = MutableLiveData()
    fun addOder(oder:Oder){
        viewModelScope.launch {
            try{
                val repone=RetrofitInstance.api.addoder(oder)
                dataoder.value=repone
            }catch (e :Exception){
                e.printStackTrace()
            }
        }
    }
}