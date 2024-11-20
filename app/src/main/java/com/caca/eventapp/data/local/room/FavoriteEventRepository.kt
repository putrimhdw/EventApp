package com.caca.eventapp.data.local.room

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val favoriteUserDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventDatabase.getDatabase(application)
        favoriteUserDao = db.favoriteUserDao()
    }


    fun insertFavoriteUser(favoriteUser: FavoriteEvent) {
        executorService.execute { favoriteUserDao.insertFavoriteUser(favoriteUser) }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteEvent) {
        executorService.execute { favoriteUserDao.deleteFavoriteUser(favoriteUser) }
    }

    fun getFavoriteUserByUsername(id: Int) = favoriteUserDao.getFavoriteUserByUsername(id)


    fun getAllFavoriteUser() = favoriteUserDao.getAllFavoriteUser()


}