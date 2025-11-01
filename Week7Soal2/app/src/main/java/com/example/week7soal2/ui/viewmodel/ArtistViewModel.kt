package com.example.week7soal2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7soal2.data.container.AudioDbContainer
import com.example.week7soal2.ui.model.AlbumModel
import com.example.week7soal2.ui.model.ArtistModel
import kotlinx.coroutines.launch

// Di ArtistViewModel
sealed class ArtistUiState {
    object Loading : ArtistUiState()
    data class Success(
        val artist: ArtistModel,
        val albums: List<AlbumModel>,
    ) : ArtistUiState()
    data class Error(val message: String) : ArtistUiState()
}

class ArtistViewModel(
    private val container: AudioDbContainer = AudioDbContainer()
) : ViewModel() {

    var uiState: ArtistUiState by mutableStateOf(ArtistUiState.Loading)
        private set

    private val artistName = "Taylor Swift"

    init {
        loadArtistData()  // INI PENTING! Auto-load saat ViewModel dibuat
    }

    private fun loadArtistData() {
        viewModelScope.launch {
            uiState = ArtistUiState.Loading
            try {
                val artist = container.audioDbRepository.getArtistInfo(artistName)
                val albums = container.audioDbRepository.getAlbumsByArtist(artistName)

                if (artist != null) {
                    uiState = ArtistUiState.Success(artist, albums)
                } else {
                    uiState = ArtistUiState.Error("Artist not found")
                }
            } catch (e: Exception) {
                uiState = ArtistUiState.Error("Error: Tidak ada koneksi internet")
            }
        }
    }

    fun retry() {
        loadArtistData()
    }
}