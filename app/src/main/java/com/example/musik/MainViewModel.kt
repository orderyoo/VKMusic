package com.example.musik

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, context: Context): ViewModel(){

    val _user = MutableLiveData<Users>()

    val playerManager = AudioPlayerManager(context)

    val currentPlaylist = MutableLiveData<Playlist?>()

    val _Playlists = MutableLiveData<Playlists>()
    val _Audios = MutableLiveData<Audios>()
    val _audiosFromPlaylist = MutableLiveData<Audios>()

    var _searchLine = ""
    var listener: Observer? = null


    fun getUser(){
        viewModelScope.launch {
            _user.value = repository.getUser()
        }
    }

    fun getPlaylists(){
        viewModelScope.launch {
            _Playlists.value = repository.getPlaylists()
        }
    }
    fun getAudios(){
        viewModelScope.launch {
            _Audios.value = repository.getAudios()
        }
    }
    fun getAudiosFromPlaylist(id: Int){
        viewModelScope.launch {
           _audiosFromPlaylist.value = repository.getAudiosFromPlaylist(id)
        }
    }
    fun searchAudios(string: String){
        viewModelScope.launch {
            _audiosFromPlaylist.value = repository.searchAudio(string)
        }
    }
}