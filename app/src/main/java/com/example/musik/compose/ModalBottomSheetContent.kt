package com.example.musik.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MicExternalOn
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModalBottomSheetContent(

){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.weight(0.15f),
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Filled.MicExternalOn, "Артист")
                }
                Box(
                    modifier = Modifier.weight(0.85f),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Перейти к артисту",
                        fontSize = 18.sp)
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.weight(0.15f),
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Filled.PlaylistAdd, "Добавить в плейлист")
                }
                Box(
                    modifier = Modifier.weight(0.85f),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Добавить в плейлист",
                        fontSize = 18.sp)
                }
            }
        }
    }
}