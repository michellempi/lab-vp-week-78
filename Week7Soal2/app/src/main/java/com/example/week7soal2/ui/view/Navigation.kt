package com.example.week7soal2.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Artist : Screen("artist")
    object AlbumDetail : Screen("album/{albumId}") {
        fun createRoute(albumId: String) = "album/$albumId"
    }
}

@Composable
fun ArtistExplorerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Artist.route  // Start dari Artist screen
    ) {
        composable(Screen.Artist.route) {
            ArtistScreen(
                onAlbumClick = { albumId ->
                    navController.navigate(Screen.AlbumDetail.createRoute(albumId))
                }
            )
        }

        composable(
            route = Screen.AlbumDetail.route,
            arguments = listOf(
                navArgument("albumId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
            AlbumDetailScreen(
                albumId = albumId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}