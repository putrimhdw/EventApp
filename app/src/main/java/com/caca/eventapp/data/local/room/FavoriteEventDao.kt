package com.caca.eventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: FavoriteEvent)

    @Delete
    fun deleteFavoriteUser(favoriteUser: FavoriteEvent)

    @Query("SELECT * from favoriteevent")
    fun getAllFavoriteUser(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getFavoriteUserByUsername(id: Int): LiveData<FavoriteEvent>
}