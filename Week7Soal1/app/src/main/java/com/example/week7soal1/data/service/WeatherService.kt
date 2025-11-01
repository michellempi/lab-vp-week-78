package com.example.week7soal1.data.service

import com.example.week7soal1.data.dto.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ResponseWeather
}