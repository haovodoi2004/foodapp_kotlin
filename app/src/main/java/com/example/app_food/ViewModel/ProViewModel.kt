package com.example.app_food.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Product
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch


class ProViewModel:ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    val Product= MutableLiveData<List<Product>>()
    val Product1= MutableLiveData<List<Product>>()
    val Product2= MutableLiveData<List<Product>>()
    val pro=MutableLiveData<Product>()

    private val _products = mutableStateOf<Map<String, Product>>(emptyMap())
    val products = _products

    fun clearSearchResults() {
        Product1.postValue(emptyList())
    }

    fun gettprobyname(name : String){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.getproductbyname(name)
            try {
                if(respone.isNotEmpty()){
                    Product2.postValue(respone)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun getprobyname(name : String){
        viewModelScope.launch {
            val respone = RetrofitInstance.api.getproductbyname(name)
            try {
                if(respone.isNotEmpty()){
                    Product1.postValue(respone)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    fun addProduct(pro: Product){
        viewModelScope.launch {
            try {
                val respone = RetrofitInstance.api.addproductt(pro)
                if(respone.isSuccessful){
                    val updatedList = RetrofitInstance.api.getproduct()
//                    Product.postValue(updatedList)
                    _products.value=updatedList.associateBy { it.id!! }
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
                    Product.postValue(response)
                }
            } catch (e: Exception) {
                Log.e("Error", e.localizedMessage ?: "Unknown Error", e)
                Product.postValue(emptyList())
            }
        }
    }

    fun fetchProduct1() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getproduct()
                if (response.isNotEmpty()) {
                    Product1.postValue(response)
                } else {
                    Product1.postValue(response)
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
                    gettProById(idpro)
                    fetchProduct()
                    getProById(idpro)
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


    fun gettProById(id: String) {
        // Fetch product from repository
        viewModelScope.launch {
            val product = RetrofitInstance.api.getProbyid(id)
            if (product.isSuccessful) {
                val product = product.body()
                product?.let {
                    _products.value = _products.value.toMutableMap().apply { put(id, it) }
                }
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