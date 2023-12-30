@file:Suppress("DEPRECATION")

package com.example.musik.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musik.Audio
import com.example.musik.AudioPlayerManager
import com.example.musik.MainActivity
import com.example.musik.MainViewModel
import com.example.musik.compose.ViewAudioForList
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PlaylistScreen(
    mainActivity: MainActivity,
    viewModel: MainViewModel,
    playerManager: AudioPlayerManager,
    navController: NavController,
){
    var listAudio by remember { mutableStateOf(listOf<Audio>()) }

    LaunchedEffect(Unit) {
        viewModel.getAudiosFromPlaylist(viewModel.currentPlaylist.value!!.id)
        viewModel._audiosFromPlaylist.observe(mainActivity) {
            listAudio = it.response.items
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        LazyColumn() {

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Card(
                            modifier = Modifier
                                .size(200.dp)
                                .padding(5.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                        ){
                            Image(painter = rememberGlidePainter(
                                request = viewModel.currentPlaylist.value!!.photo.photo300),
                                contentDescription = null)
                        }
                        Box(contentAlignment = Alignment.Center){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = viewModel.currentPlaylist.value!!.title,
                                    color = MaterialTheme.colorScheme.primary)
                                Text(text = viewModel.currentPlaylist.value!!.mainArtist?.first()?.name ?: " ",
                                    color = Color.Gray)
                                Text(text = viewModel.currentPlaylist.value!!.genres?.firstOrNull()?.name ?: " ",
                                        color = Color.Gray)
                                Text(text = formatNumber(viewModel.currentPlaylist.value!!.plays) + " прослушиваний",
                                    color = Color.Gray)
                            }
                        }
                    }
                }
            }

            items(listAudio){ item ->
                ViewAudioForList(viewModel = viewModel, playerManager = playerManager, item = item, listAudio)
            }
        }
    }
}

fun formatNumber(number: Int): String {
    return when {
        number < 10000 -> number.toString()
        number < 1_000_000 -> String.format("%.1f K", number / 1000.0)
        else -> String.format("%.1f M", number / 1_000_000.0)
    }
}

