package com.example.week7soal2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.week7soal2.ui.model.AlbumModel
import com.example.week7soal2.ui.model.TrackModel
import com.example.week7soal2.ui.viewmodel.AlbumDetailUiState
import com.example.week7soal2.ui.viewmodel.AlbumDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumId: String,
    onBackClick: () -> Unit,
    viewModel: AlbumDetailViewModel = viewModel()
) {
    LaunchedEffect(albumId) {
        viewModel.loadAlbumDetail(albumId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBg)
    ) {
        when (val state = viewModel.uiState) {
            is AlbumDetailUiState.Loading -> {
                LoadingScreen()
            }
            is AlbumDetailUiState.Success -> {
                AlbumDetailContent(
                    album = state.album,
                    tracks = state.tracks,
                    onBackClick = onBackClick
                )
            }
            is AlbumDetailUiState.Error -> {
                ErrorScreen(
                    message = state.message,
                    onRetry = { viewModel.loadAlbumDetail(albumId) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailContent(
    album: AlbumModel,
    tracks: List<TrackModel>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = album.name,
                        color = GruvboxFg
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = GruvboxYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GruvboxBg1
                )
            )
        },
        containerColor = GruvboxBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Album Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = album.thumb,
                        contentDescription = album.name,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = album.name,
                        color = GruvboxYellow,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = album.artist,
                        color = GruvboxFg,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "${album.year} • ${album.genre}",
                        color = GruvboxFg.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    if (album.description.isNotBlank()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = GruvboxBg1,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GruvboxBg2)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Description",
                                    color = GruvboxOrange,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = album.description,
                                    color = GruvboxFg,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tracks Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tracks",
                            color = GruvboxYellow,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${tracks.size} songs",
                            color = GruvboxFg.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Track List
            items(tracks) { track ->
                TrackItem(track = track)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TrackItem(track: TrackModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        color = GruvboxBg1,
        shape = RoundedCornerShape(8.dp),
        border = androidx.
        compose.foundation.BorderStroke(1.dp, GruvboxBg2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Track Number
                Surface(
                    modifier = Modifier.size(32.dp),
                    color = GruvboxBg2,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = track.trackNumber.toString(),
                            color = GruvboxYellow,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Track Name
                Text(
                    text = track.name,
                    color = GruvboxFg,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            // Duration
            Text(
                text = track.duration,
                color = GruvboxFg.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}