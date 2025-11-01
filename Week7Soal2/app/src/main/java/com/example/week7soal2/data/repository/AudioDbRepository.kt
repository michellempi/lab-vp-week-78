package com.example.week7soal2.data.repository

import android.util.Log
import com.example.week7soal2.data.service.AudioDbService
import com.example.week7soal2.ui.model.AlbumModel
import com.example.week7soal2.ui.model.ArtistModel
import com.example.week7soal2.ui.model.TrackModel

class AudioDbRepository(
    private val audioDbService: AudioDbService
) {
    private val TAG = "AudioDbRepository"

    // Get Artist Information
    suspend fun getArtistInfo(artistName: String): ArtistModel? {
        Log.d(TAG, "=== Fetching Artist Info ===")
        Log.d(TAG, "Artist Name: $artistName")

        val response = audioDbService.searchArtist(artistName)
        val artist = response.artists?.firstOrNull()

        Log.d(TAG, "Artist found: ${artist != null}")
        if (artist != null) {
            Log.d(TAG, "Artist ID: ${artist.idArtist}")
            Log.d(TAG, "Artist Genre: ${artist.strGenre}")
        }

        return artist?.let {
            ArtistModel(
                id = it.idArtist,
                name = it.strArtist,
                genre = it.strGenre,
                biography = it.strBiographyEN,
                thumb = it.strArtistThumb,
                banner = it.strArtistBanner
            )
        }
    }

    // Get Albums by Artist
    suspend fun getAlbumsByArtist(artistName: String): List<AlbumModel> {
        Log.d(TAG, "=== Fetching Albums ===")
        Log.d(TAG, "Artist Name: $artistName")

        val response = audioDbService.searchAlbumsByArtist(artistName)

        Log.d(TAG, "API Response album field: ${response.album}")
        Log.d(TAG, "Album count: ${response.album?.size ?: 0}")

        response.album?.forEachIndexed { index, album ->
            Log.d(TAG, "Album $index: ${album.strAlbum} (${album.intYearReleased})")
        }

        return response.album?.map { album ->
            AlbumModel(
                id = album.idAlbum,
                name = album.strAlbum,
                artist = album.strArtist,
                year = album.intYearReleased,
                genre = album.strGenre,
                thumb = album.strAlbumThumb ?: "",
                description = album.strDescriptionEN ?: ""
            )
        } ?: emptyList()
    }

    // Get Album Detail
    suspend fun getAlbumDetail(albumId: String): AlbumModel? {
        Log.d(TAG, "=== Fetching Album Detail ===")
        Log.d(TAG, "Album ID: $albumId")

        val response = audioDbService.getAlbumDetail(albumId)
        val album = response.album?.firstOrNull()

        Log.d(TAG, "Album found: ${album != null}")

        return album?.let {
            AlbumModel(
                id = it.idAlbum,
                name = it.strAlbum,
                artist = it.strArtist,
                year = it.intYearReleased,
                genre = it.strGenre,
                thumb = it.strAlbumThumb ?: "",
                description = it.strDescriptionEN ?: ""
            )
        }
    }

    // Get Album Tracks
    suspend fun getAlbumTracks(albumId: String): List<TrackModel> {
        Log.d(TAG, "=== Fetching Album Tracks ===")
        Log.d(TAG, "Album ID: $albumId")

        val response = audioDbService.getAlbumTracks(albumId)

        Log.d(TAG, "Track count: ${response.track?.size ?: 0}")

        return response.track?.map { track ->
            TrackModel(
                id = track.idTrack,
                name = track.strTrack,
                duration = formatDuration(track.intDuration),
                trackNumber = track.intTrackNumber.toIntOrNull() ?: 0
            )
        }?.sortedBy { it.trackNumber } ?: emptyList()
    }

    // Helper function to format duration from milliseconds to MM:SS
    private fun formatDuration(durationMs: String?): String {
        if (durationMs == null) return "0:00"

        val totalSeconds = (durationMs.toLongOrNull() ?: 0) / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        return String.format("%d:%02d", minutes, seconds)
    }
}