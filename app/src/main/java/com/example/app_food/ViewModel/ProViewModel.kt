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

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    val Product= MutableLiveData<List<Product>>()

    fun addProduct(pro: Product){
        viewModelScope.launch {
            try {
                val respone = RetrofitInstance.api.addproductt(pro)
                if(respone.isSuccessful){
                    val updatedList = RetrofitInstance.api.getproduct()
                    Product.postValue(updatedList)
                    fetchProduct()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteProduct(id : String){
        viewModelScope.launch {
            try {
                val respone= RetrofitInstance.api.deleteproduct(id)
                if(respone.isSuccessful){
                    fetchProduct()
                }else{

                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun fetchProduct() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getproduct()
                if (response.isNotEmpty()) {
                    Product.postValue(response)
                } else {
                    Product.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage ?: "Unknown Error", e)
                Product.postValue(emptyList())
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

    fun getProBycategoryy(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProbycategory(category)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Product.postValue(it) // Gán vào LiveData chính
                    }
                } else {
                    Log.e("getProBycategoryy", "Error: ${response.errorBody()?.string()}")
                    Product.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("getProBycategoryy", "Exception: ${e.localizedMessage}", e)
                Product.postValue(emptyList())
            }
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