package com.caca.eventapp.utils.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.caca.eventapp.data.local.preference.SettingPreferences
import com.caca.eventapp.ui.detail.DetailViewModel
import com.caca.eventapp.ui.favorite.FavoriteViewModel
import com.caca.eventapp.ui.setting.SettingViewModel

class ViewModelFactoryWithSettingPreferences(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}


class ViewModelFactoryWithApplication private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactoryWithApplication? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactoryWithApplication {
            if (INSTANCE == null) {
                synchronized(ViewModelFactoryWithApplication::class.java) {
                    INSTANCE = ViewModelFactoryWithApplication(application)
                }
            }
            return INSTANCE as ViewModelFactoryWithApplication
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

