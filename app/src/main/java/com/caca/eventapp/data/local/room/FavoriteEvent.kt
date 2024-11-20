package com.caca.eventapp.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEvent (
    @PrimaryKey
    var id : Int,
    var title : String,
    var description : String,
    var image : String
)