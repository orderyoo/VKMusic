@file:Suppress("DEPRECATION")

package com.example.musik.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musik.Audio
import com.example.musik.AudioPlayerManager
import com.example.musik.MainActivity
import com.example.musik.MainViewModel
import com.example.musik.Playlist
import com.example.musik.compose.ViewAudioForList
import com.example.musik.security.SecurityData
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun HomeScreen(
    mainActivity: MainActivity,
    viewModel: MainViewModel,
    playerManager: AudioPlayerManager,
    navController: NavController,
){
    var listPlaylist by remember { mutableStateOf(listOf<Playlist>()) }
    var listAudioForHome by remember { mutableStateOf(listOf<Audio>()) }

    LaunchedEffect(Unit) {
        viewModel.getPlaylists()
        viewModel._Playlists.observe(mainActivity) {
            listPlaylist = it.response.items
        }

        viewModel.getAudios()
        viewModel._Audios.observe(mainActivity) {
            listAudioForHome = it.response.items
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .weight(0.85f)
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
            ){
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .weight(0.125f),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(Icons.Filled.Search, contentDescription = "Поиск",
                            tint = MaterialTheme.colorScheme.inverseSurface)
                    }

                    var text by remember { mutableStateOf(viewModel._searchLine)}
                    OutlinedTextField(value = text,
                        onValueChange = {
                            text = it
                            viewModel._searchLine = text
                        },
                        modifier = Modifier
                            .weight(0.75f),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        keyboardActions = KeyboardActions(
                            onDone  = {
                                navController.navigate("search")
                               }
                        ),
                        placeholder = { Text(text = "Поиск") },
                        singleLine = true,
                        shape = RoundedCornerShape(15.dp)
                    )

                }
            }

            var expanded by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .weight(0.15f)
                    .heightIn(max = 70.dp)
                    .clickable { expanded = !expanded }
                    .padding(top = 5.dp, end = 5.dp, bottom = 5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
            ){

                Image(painter = rememberGlidePainter(
                    request = viewModel._user.value!!.response[0].photo200),
                    contentDescription = null)

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Box(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = viewModel._user.value!!.response[0].firstName +
                                " " + viewModel._user.value!!.response[0].lastName,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary)
                    }

                    DropdownMenuItem(
                        text = { Text("Выйти",
                            color = MaterialTheme.colorScheme.primary) },
                        onClick = {
                            val securityData = SecurityData(mainActivity)
                            securityData.clearData()
                            mainActivity.checkLogIn(mainActivity)
                        },
                        leadingIcon = { Icon(Icons.Filled.Output,
                            contentDescription = "Выход",
                            tint = MaterialTheme.colorScheme.inverseSurface)}
                        
                    )
                    DropdownMenuItem(
                        text = { Text("О приложении",
                            color = MaterialTheme.colorScheme.primary) },
                        onClick = { navController.navigate("info") },
                        leadingIcon = {Icon(Icons.Filled.Info,
                            contentDescription = "Инфо",
                            tint = MaterialTheme.colorScheme.inverseSurface)}
                    )
                }
            }
        }

        // список плейлистов + список "моих" аудио
        LazyColumn(){
            //список плейлистов
            item{
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                ){
                    Column {
                        Text(text = "Плейлисты",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            items(listPlaylist){item->
                                Box(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .width(128.dp)
                                        .clickable {
                                            viewModel.currentPlaylist.value = item
                                            navController.navigate("openPlaylist")
                                        }
                                ){
                                    Column {
                                        Card(
                                            modifier = Modifier
                                                .size(125.dp),
                                            shape = RoundedCornerShape(15.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
                                        ){
                                            @Suppress("DEPRECATION")
                                            Image(painter = rememberGlidePainter(request = item.photo.photo300), contentDescription = null)
                                        }
                                        Column(Modifier.padding(5.dp)) {
                                            Text(text = item.title,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.primary,
                                                overflow = TextOverflow.Ellipsis,
                                                softWrap = false)
                                            Text(text = item.mainArtist?.first()?.name ?: "плейлист",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                overflow = TextOverflow.Ellipsis,
                                                softWrap = false)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //cписок моих аудио
            items(listAudioForHome){ item ->
                ViewAudioForList(viewModel = viewModel, playerManager = playerManager, item = item, listAudioForHome)
            }
        }
    }
}