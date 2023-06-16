package com.example.konversimatauang.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.konversimatauang.network.UangApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val data = MutableLiveData<List<Uang>>()
    private val status = MutableLiveData<UangApi.ApiStatus>()

    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(UangApi.ApiStatus.LOADING)
            try {
                data.postValue(UangApi.service.getUang())
                status.postValue(UangApi.ApiStatus.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(UangApi.ApiStatus.FAILED)

            }
        }
    }


    fun getData(): LiveData<List<Uang>> = data
    fun getStatus(): LiveData<UangApi.ApiStatus> = status

}