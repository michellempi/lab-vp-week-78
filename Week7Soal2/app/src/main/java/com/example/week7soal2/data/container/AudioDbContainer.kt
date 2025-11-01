package com.example.week7soal2.data.container

import com.example.week7soal2.data.repository.AudioDbRepository
import com.example.week7soal2.data.service.AudioDbService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AudioDbContainer {
    private val BASE_URL = "https://www.theaudiodb.com/api/v1/json/2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val audioDbService: AudioDbService by lazy {
        retrofit.create(AudioDbService::class.java)
    }

    val audioDbRepository: AudioDbRepository by lazy {
        AudioDbRepository(audioDbService)
    }
}