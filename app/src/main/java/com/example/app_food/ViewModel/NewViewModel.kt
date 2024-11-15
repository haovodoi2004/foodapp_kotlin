package com.example.app_food.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.New
import com.example.app_food.Model.Protype
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class NewViewModel:ViewModel() {
    private val _NewItems= mutableStateListOf<New>()
    val NewItems:List<New> get()=_NewItems

    fun fetchNew(){
        viewModelScope.launch {
            try{
                val respone= RetrofitInstance.api.getnew()
                println("Fetched data: $respone")
                Log.e("API Response"," Fetches data : $respone")
                _NewItems.addAll(respone)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}