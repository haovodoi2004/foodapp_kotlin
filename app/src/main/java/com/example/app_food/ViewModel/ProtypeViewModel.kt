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

class ProtypeViewModel : ViewModel() {
    val protypeItems = MutableLiveData<List<Protype>>() // Quản lý danh sách bằng LiveData

    fun fetchProtype() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getprotype()
                if (response.isNotEmpty()) {
                    protypeItems.postValue(response)
                } else {
                    protypeItems.postValue(emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addprotype(protype: Protype) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addprotype(protype)
                if (response.isSuccessful) {
                    val updatedList = RetrofitInstance.api.getprotype()
                    protypeItems.postValue(updatedList)
                    fetchProtype()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteprotype(name: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteprotype(name)
                if (response.isSuccessful) {
                    fetchProtype()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateprotype(name: String, protype: Protype) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateprotype(name, protype)
                if (response.isSuccessful) {
                    fetchProtype()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
