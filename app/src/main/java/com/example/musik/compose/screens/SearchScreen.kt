package com.example.musik.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musik.Audio
import com.example.musik.AudioPlayerManager
import com.example.musik.MainActivity
import com.example.musik.MainViewModel
import com.example.musik.compose.ViewAudioForList

@Composable
fun SearchScreen(
    mainActivity: MainActivity,
    viewModel: MainViewModel,
    playerManager: AudioPlayerManager,
    navController: NavController,
){
    var list by remember { mutableStateOf(listOf<Audio>()) }
    LaunchedEffect(Unit) {
        viewModel.searchAudios(viewModel._searchLine)
        viewModel._audiosFromPlaylist.observe(mainActivity) {
            list = it.response.items
            viewModel._searchLine = ""
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        LazyColumn(){
            items(list){ item ->
                ViewAudioForList(viewModel = viewModel, playerManager = playerManager, item = item, list)
            }
        }
    }

}