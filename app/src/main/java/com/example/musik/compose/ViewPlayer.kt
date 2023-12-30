@file:Suppress("DEPRECATION")

package com.example.musik.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musik.AudioPlayerManager
import com.example.musik.PlayerEventListener

@Composable
fun ViewPlayer(
    playerManager: AudioPlayerManager
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // карта с изображением трека
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Card(
                modifier = Modifier
                    .size(300.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
            ){
                Text(text = "IMAGE")
            }
        }
        //скролл по аудио
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            var sliderPosition by remember { mutableFloatStateOf(0f) }
            var startTrack by remember { mutableStateOf("0:00") }
            var endTrack by remember { mutableStateOf("0:00") }

            val listener = object : PlayerEventListener {
                override fun onPositionChanged(currentPosition: Long, duration: Long) {
                    sliderPosition =  currentPosition.toFloat() / duration.toFloat()
                    startTrack = playerManager.getFormatTime(currentPosition)
                    endTrack = playerManager.getFormatTime(duration-currentPosition)
                }
            }
            playerManager.setPlayerEventListener(listener)
            Box(
                Modifier.weight(0.15f),
                Alignment.Center
            ){
                Text(text = startTrack, fontSize = 11.sp)
            }
            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    playerManager.seekTo(it)},
                modifier = Modifier.weight(0.7f),
                colors = SliderDefaults.colors(
                    Color.Red,
                    Color.Gray
                )
            )
            Box(
                Modifier.weight(0.15f),
                Alignment.Center
            ){
                Text(text = "- $endTrack", fontSize = 11.sp)
            }
        }
        // кнопки назад пауза вперёд
        Row{
            Box(modifier = Modifier
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Button(
                    modifier = Modifier
                        .size(75.dp),
                    onClick = { playerManager.previousAudio() },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black),
                    content = { Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Предыдущий трек", tint = MaterialTheme.colorScheme.inverseSurface)}
                )
            }

            Box(modifier = Modifier
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Button(
                    modifier = Modifier.size(75.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black),
                    onClick = {
                        playerManager.switchPlaying()
                    },
                    content = {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = MaterialTheme.colorScheme.inverseSurface)
                        //Icon(Icons.Filled.Pause, contentDescription = "Pause", tint = MaterialTheme.colorScheme.inverseSurface)
                    }
                )
            }
            Box(modifier = Modifier
                .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Button(
                    modifier = Modifier.size(75.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black),
                    onClick = { playerManager.nextAudio() },
                    content = { Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Следующий трек", tint = MaterialTheme.colorScheme.inverseSurface)}
                )
            }
        }
    }
}