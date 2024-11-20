package com.caca.eventapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caca.eventapp.data.local.room.FavoriteEvent
import com.caca.eventapp.data.local.room.FavoriteEventRepository

class FavoriteViewModel (application: Application) : ViewModel() {
    private val _userFavoriteList = MutableLiveData<List<FavoriteEvent>>()
    val userFavoriteList: LiveData<List<FavoriteEvent>> = _userFavoriteList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteUserRepository: FavoriteEventRepository = FavoriteEventRepository(application)
    fun gettingUserFavorite() = favoriteUserRepository.getAllFavoriteUser()
}