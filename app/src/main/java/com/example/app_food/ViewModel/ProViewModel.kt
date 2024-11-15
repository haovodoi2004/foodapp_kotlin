package com.example.app_food.ViewModel

<<<<<<< HEAD
=======
import android.util.Log
import androidx.lifecycle.LiveData
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Product
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class ProViewModel:ViewModel() {
    val proo : MutableLiveData<Response<List<Product>>> = MutableLiveData()
<<<<<<< HEAD

=======
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
    fun getProBycategoryy(category : String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.getProbycategory(category)
            proo.value = response
        }
    }
<<<<<<< HEAD
=======



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
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
}