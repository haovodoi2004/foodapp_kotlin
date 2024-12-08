package com.example.app_food.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_food.Model.Oder

import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class OderViewModel() : ViewModel(){
    val dataoder : MutableLiveData<Response<Oder>> = MutableLiveData()
    val oder = MutableLiveData<List<Oder>>()

    fun updateoder(id : String , oder: Oder){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.updateoder(id,oder)
            try {
                if(respone.isSuccessful){
                    fetchoder()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchoder(){
        viewModelScope.launch {
            try {
                val respone = RetrofitInstance.api.getlistoder()
                if(respone.isEmpty()){
                    oder.postValue(respone)
                }else{
                    oder.postValue(respone)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

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