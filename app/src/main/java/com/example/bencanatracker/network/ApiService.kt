package com.example.bencanatracker.network

import ReportsResponse
import com.example.bencanatracker.response.FloodGaugesResponse
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {

    @GET("reports?timeperiod=604800")
        suspend fun getReports(): ReportsResponse

    @GET("floodgauges?admin=ID-JK")
    suspend fun getFloodGauges(): List<FloodGaugesResponse>

    companion object {
        private const val BASE_URL = "https://data.petabencana.id/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
