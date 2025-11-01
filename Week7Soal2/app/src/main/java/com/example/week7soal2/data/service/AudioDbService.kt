package com.example.week7soal2.data.service

import com.example.week7soal2.data.dto.AlbumResponse
import com.example.week7soal2.data.dto.ArtistResponse
import com.example.week7soal2.data.dto.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AudioDbService {

    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): ArtistResponse

    @GET("searchalbum.php")
    suspend fun searchAlbumsByArtist(
        @Query("s") artistName: String
    ): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetail(
        @Query("m") albumId: String
    ): AlbumResponse

    @GET("track.php")
    suspend fun getAlbumTracks(
        @Query("m") albumId: String
    ): TrackResponse
}