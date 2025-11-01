package com.example.week7soal1.data.container

import com.example.week7soal1.data.repository.WeatherRepository
import com.example.week7soal1.data.service.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherContainer {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val API_KEY = "213a28a81bc7596d351a0306e6a7370a"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService, API_KEY)
    }
}