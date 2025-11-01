package com.example.week7soal1.ui.model

data class WeatherModel(
    val cityName: String,
    val date: String,
    val weatherCondition: String,
    val weatherIcon: String,
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Double,
    val feelsLike: Int,
    val rainfall: Double,
    val pressure: Int,
    val clouds: Int,
    val sunrise: String,
    val sunset: String
)