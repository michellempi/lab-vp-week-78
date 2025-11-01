package com.example.week7soal1.data.repository

import com.example.week7soal1.data.dto.ResponseWeather
import com.example.week7soal1.ui.model.WeatherModel
import com.example.week7soal1.data.service.WeatherService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherRepository(
    private val weatherService: WeatherService,
    private val apiKey: String
) {
    suspend fun getWeatherByCity(cityName: String): WeatherModel {
        val response = weatherService.getWeather(cityName, apiKey)
        return convertToModel(response)
    }

    private fun convertToModel(dto: ResponseWeather): WeatherModel {
        return WeatherModel(
            cityName = dto.name,
            date = getCurrentDate(),
            weatherCondition = dto.weather.firstOrNull()?.main ?: "Clear",
            weatherIcon = dto.weather.firstOrNull()?.icon ?: "01d",
            temperature = dto.main.temp.toInt(),
            humidity = dto.main.humidity,
            windSpeed = dto.wind.speed,
            feelsLike = dto.main.feels_like.toInt(),
            rainfall = 0.0, // Set default 0.0 karena API tidak selalu kirim data rain
            pressure = dto.main.pressure,
            clouds = dto.clouds.all,
            sunrise = formatTime(dto.sys.sunrise.toLong()),
            sunset = formatTime(dto.sys.sunset.toLong())
        )
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MMMM d", Locale.ENGLISH)
        return dateFormat.format(Date())
    }

    private fun formatTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val timeFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
        return timeFormat.format(date)
    }
}