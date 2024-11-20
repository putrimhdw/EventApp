package com.caca.eventapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caca.eventapp.data.local.room.FavoriteEvent
import com.caca.eventapp.data.local.room.FavoriteEventRepository
import com.caca.eventapp.data.response.EventDetailResponse
import com.caca.eventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _event = MutableLiveData<EventDetailResponse>()
    val event: LiveData<EventDetailResponse> = _event

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val favoriteUserRepository: FavoriteEventRepository = FavoriteEventRepository(application)

    fun insertFavoriteUser(favoriteUser: FavoriteEvent) {
        favoriteUserRepository.insertFavoriteUser(favoriteUser)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteEvent) {
        favoriteUserRepository.deleteFavoriteUser(favoriteUser)
    }

    fun getFavoriteUserByUsername(id: Int) = favoriteUserRepository.getFavoriteUserByUsername(id)


    fun getEventDetail (id : Int){
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEventDetail(id)
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(call: Call<EventDetailResponse>, response: Response<EventDetailResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _event.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _errorMessage.value = t.message.toString()
            }

        })
    }


    companion object{
        private const val TAG = "DetailViewModel"
    }

}