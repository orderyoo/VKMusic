package com.example.musik.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensitySmall
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musik.Audio
import com.example.musik.AudioPlayerManager
import com.example.musik.MainViewModel
import com.example.musik.Observer
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ViewAudioForList(
    viewModel: MainViewModel,
    playerManager: AudioPlayerManager,
    item: Audio,
    list: List<Audio>
){
    val observer: Observer? = viewModel.listener

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
    ){
        Row(modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (playerManager.playingPlaylist.isEmpty() || playerManager.playingPlaylist != list) {
                    if (playerManager.playingPlaylist.isNotEmpty())
                        playerManager.clearMediaItems()
                    playerManager.setPlaylist(list)
                    playerManager.playingPlaylist = list
                }
                if (viewModel.playerManager.currentAudio.value == null || !(viewModel.playerManager.currentAudio.value!!.equals(item))) {
                    playerManager.playAudioUserSelected(list, item)
                    viewModel.playerManager.currentAudio.value = item
                } else {
                    playerManager.switchPlaying()

                }
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(0.20f),
                contentAlignment = Alignment.Center){
                Card(
                    modifier = Modifier
                        .size(50.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outline),
                    content = {
                        //Image(painter = rememberGlidePainter( request = viewModel.currentPlaylist.value!!.photo.photo300), contentDescription = null)}
                    }
                )
            }
            Column(modifier = Modifier.weight(0.80f)) {
                Text(text = item.title,
                    modifier = Modifier
                        .padding(5.dp),
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false)
                Text(text = item.artist,
                    modifier = Modifier
                        .padding(5.dp),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false)
            }

            val date = Date((item.duration.toLong()) * 1000)
            val dateFormat = SimpleDateFormat("mm:ss")
            val formattedDate = dateFormat.format(date)

            Text(text = formattedDate,
                color = Color.Gray,
                fontSize = 12.sp)

            IconButton(modifier = Modifier.wrapContentSize(),
                onClick = { observer?.onEdit() },
                content = {Icon(Icons.Filled.DensitySmall, contentDescription = "Меню редактирования", Modifier.size(15.dp), tint = MaterialTheme.colorScheme.inverseSurface)}
            )
        }
    }
}