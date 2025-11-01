package com.example.week7soal2.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.week7soal2.ui.model.AlbumModel
import com.example.week7soal2.ui.model.ArtistModel
import com.example.week7soal2.ui.viewmodel.ArtistUiState
import com.example.week7soal2.ui.viewmodel.ArtistViewModel

// Gruvbox Colors
val GruvboxBg = Color(0xFF282828)
val GruvboxFg = Color(0xFFEBDBB2)
val GruvboxYellow = Color(0xFFFABD2F)
val GruvboxOrange = Color(0xFFFE8019)
val GruvboxRed = Color(0xFFFB4934)
val GruvboxGreen = Color(0xFFB8BB26)
val GruvboxAqua = Color(0xFF8EC07C)
val GruvboxBg1 = Color(0xFF3C3836)
val GruvboxBg2 = Color(0xFF504945)

@Composable
fun ArtistScreen(
    onAlbumClick: (String) -> Unit,
    viewModel: ArtistViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBg)
    ) {
        when (val state = viewModel.uiState) {
            is ArtistUiState.Loading -> {
                LoadingScreen()
            }
            is ArtistUiState.Success -> {
                ArtistContent(
                    artist = state.artist,
                    albums = state.albums,
                    onAlbumClick = onAlbumClick
                )
            }
            is ArtistUiState.Error -> {
                ErrorScreen(
                    message = state.message,
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = GruvboxYellow,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading...",
                color = GruvboxFg,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Error",
                color = GruvboxRed,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = GruvboxFg,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = onRetry,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = GruvboxYellow
                ),
                border = androidx.compose.foundation.BorderStroke(2.dp, GruvboxYellow)
            ) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun ArtistContent(
    artist: ArtistModel,
    albums: List<AlbumModel>,
    onAlbumClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBg)
    ) {
        // Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E))
                    .padding(top = 32.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = artist.name,
                    color = GruvboxFg,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Artist Card
        item {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        AsyncImage(
                            model = artist.thumb,
                            contentDescription = "Artist Thumb",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = artist.name,
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = artist.genre,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Albums Header
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Albums",
                    color = GruvboxFg,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(((albums.size + 1) / 2) * 260.dp), // Dynamic height
                userScrollEnabled = false // Disable inner scroll karena sudah di LazyColumn
            ) {
                items(albums) { album ->
                    AlbumCard(
                        album = album,
                        onClick = { onAlbumClick(album.id) }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AlbumCard(
    album: AlbumModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = GruvboxBg1,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GruvboxBg2)
    ) {
        Column {
            AsyncImage(
                model = album.thumb,
                contentDescription = album.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = album.name,
                    color = GruvboxFg,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${album.year} • ${album.genre}",
                    color = GruvboxFg.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}