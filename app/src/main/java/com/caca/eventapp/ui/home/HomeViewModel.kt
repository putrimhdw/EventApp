package com.caca.eventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caca.eventapp.data.response.EventResponse
import com.caca.eventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished: LiveData<Boolean> = _isLoadingFinished

    private val _eventUpcoming = MutableLiveData<EventResponse>()
    val eventUpcoming: LiveData<EventResponse> = _eventUpcoming

    private val _eventFinished = MutableLiveData<EventResponse>()
    val eventFinished: LiveData<EventResponse> = _eventFinished

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        getEventFinished()
        getEventUpcoming()
    }

    private fun getEventFinished (){
        _isLoadingFinished.value = true

        val client = ApiConfig.getApiService().getEvent(0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingFinished.value = false
                if (response.isSuccessful){
                    _eventFinished.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingFinished.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _errorMessage.value = t.message.toString()
            }

        })
    }

    private fun getEventUpcoming (){
        _isLoadingUpcoming.value = true

        val client = ApiConfig.getApiService().getEvent(1)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingUpcoming.value = false
                if (response.isSuccessful){
                    _eventUpcoming.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _errorMessage.value = t.message.toString()
            }

        })
    }

    companion object{
        private const val TAG = "HomeViewModel"
    }


}