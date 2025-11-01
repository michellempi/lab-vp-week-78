package com.example.week7soal2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7soal2.data.container.AudioDbContainer
import com.example.week7soal2.ui.model.AlbumModel
import com.example.week7soal2.ui.model.TrackModel
import kotlinx.coroutines.launch

sealed class AlbumDetailUiState {
    object Loading : AlbumDetailUiState()
    data class Success(
        val album: AlbumModel,
        val tracks: List<TrackModel>
    ) : AlbumDetailUiState()
    data class Error(val message: String) : AlbumDetailUiState()
}

class AlbumDetailViewModel(
    private val container: AudioDbContainer = AudioDbContainer()
) : ViewModel() {

    var uiState: AlbumDetailUiState by mutableStateOf(AlbumDetailUiState.Loading)
        private set

    fun loadAlbumDetail(albumId: String) {
        viewModelScope.launch {
            uiState = AlbumDetailUiState.Loading
            try {
                val album = container.audioDbRepository.getAlbumDetail(albumId)
                val tracks = container.audioDbRepository.getAlbumTracks(albumId)

                if (album != null) {
                    uiState = AlbumDetailUiState.Success(album, tracks)
                } else {
                    uiState = AlbumDetailUiState.Error("Album not found")
                }
            } catch (e: Exception) {
                uiState = AlbumDetailUiState.Error("Error: Tidak ada koneksi internet")
            }
        }
    }
}