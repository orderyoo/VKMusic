package com.example.musik

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Repository(){

    private val vkClient = VkClient()

    fun initVkClient(context: Context){
        vkClient.init(context)
    }

    fun getUser(): Users = runBlocking{
        vkClient.getUser()
    }

    suspend fun getPlaylists(): Playlists{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getPlaylists()
        }
    }
    suspend fun getAudios(): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getAudio()
        }
    }
    suspend fun getAudiosFromPlaylist(album_id: Int): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.getAudioFromPlaylist(album_id)
        }
    }
    suspend fun searchAudio(string: String): Audios{
        return withContext(Dispatchers.IO){
            return@withContext vkClient.searchAudio(string)
        }
    }
}