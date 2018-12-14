package com.appcompanion.bernatriupuyal.appcompanion.network

import com.appcompanion.bernatriupuyal.appcompanion.models.TWStream
import com.appcompanion.bernatriupuyal.appcompanion.models.TWStreamResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService{

    @Headers("Client-ID: ry9mcpqp1kgx1yxi9z2zm42bedsso3")
    @GET("streams")
    fun getStreams() : Call<TWStreamResponse>

    companion object {
        private var retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ApiService>(ApiService::class.java)
    }


}