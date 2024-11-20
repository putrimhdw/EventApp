package com.caca.eventapp.data.retrofit
import com.caca.eventapp.data.response.EventDetailResponse
import com.caca.eventapp.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvent(
        @Query("active") active: Int
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventDetail (
        @Path("id") id : Int
    ) : Call<EventDetailResponse>
}