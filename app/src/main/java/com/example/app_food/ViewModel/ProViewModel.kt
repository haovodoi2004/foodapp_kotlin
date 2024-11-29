package com.example.app_food.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Product
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ProViewModel:ViewModel() {
    val proo : MutableLiveData<Response<List<Product>>> = MutableLiveData()
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    val Product= MutableLiveData<List<Product>>()

    fun fetchProduct(){
        viewModelScope.launch {
            val respone=RetrofitInstance.api.getproduct()
            try {
               if(respone.isNotEmpty()){
                   Product.postValue(respone)
               }else{
                   Product.postValue(emptyList())
               }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateProduct(idpro : String,pro : Product){
        viewModelScope.launch {
            try {
                val respone=RetrofitInstance.api.updatePro(idpro,pro)
                if(respone.isSuccessful){
                    fetchProduct()
                }else{
                    Log.e("API Error", "Error: ${respone.errorBody()?.string()}")
                }
            }catch (ex :Exception){
                ex.printStackTrace()
                Log.e("API Error", "Exception: ${ex.localizedMessage}")
            }
        }
    }

    fun getProBycategoryy(category : String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.getProbycategory(category)
            proo.value = response
        }
    }

    fun getProById(id : String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProbyid(id)
                if (response.isSuccessful) {
                    _product.postValue(response.body())
                }
            }catch (e: Exception) {
                Log.e("ProductDetail", "Error fetching product details", e)
            }
        }
    }
}