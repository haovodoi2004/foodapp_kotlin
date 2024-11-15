package com.example.app_food.ViewModel

import android.util.Log
import androidx.collection.mutableIntListOf
import androidx.compose.runtime.mutableStateListOf
<<<<<<< HEAD
=======
import androidx.lifecycle.MutableLiveData
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_food.Model.Protype
import com.example.app_food.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.await

class ProtypeViewModel:ViewModel() {
    private val _ProItems= mutableStateListOf<Protype>()
<<<<<<< HEAD
    val protypeItems:List<Protype> get()=_ProItems
=======
    val protypeItems = MutableLiveData<List<Protype>>()

>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)

    fun fetchProtype(){
        viewModelScope.launch {
            try{
                val respone=RetrofitInstance.api.getprotype()
                println("Fetched data: $respone")
                Log.e("API Response"," Fetches data : $respone")
<<<<<<< HEAD
                _ProItems.addAll(respone)
=======
                _ProItems.clear()  // Xóa dữ liệu cũ nếu cần
                _ProItems.addAll(respone)
                protypeItems.postValue(_ProItems)
>>>>>>> 7730f44 (nguyen anh hao day code ngay 11/19/2024)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}