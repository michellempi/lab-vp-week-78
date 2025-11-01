package com.example.week7soal1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7soal1.data.repository.WeatherRepository
import com.example.week7soal1.ui.model.WeatherModel
import kotlinx.coroutines.launch

sealed class WeatherUiState {
    object Idle : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val weather: WeatherModel) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Idle)
        private set

    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun searchWeather() {
        if (searchQuery.isBlank()) {
            uiState = WeatherUiState.Error("Please enter a city name")
            return
        }

        viewModelScope.launch {
            uiState = WeatherUiState.Loading
            try {
                val weatherData = repository.getWeatherByCity(searchQuery)
                uiState = WeatherUiState.Success(weatherData)
            } catch (e: Exception) {
                uiState = WeatherUiState.Error(
                    e.message ?: "Failed to fetch weather data"
                )
                e.printStackTrace()
            }
        }
    }
}