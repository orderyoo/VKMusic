@file:Suppress("DEPRECATION")

package com.example.musik

import android.content.Context
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.text.SimpleDateFormat
import java.util.Date

class AudioPlayerManager(private val context: Context) {

    private val player = SimpleExoPlayer.Builder(context).build()

    private var playerEventListener: PlayerEventListener? = null


    val currentAudio =  MutableLiveData<Audio>()
    var playingPlaylist = listOf<Audio>()

    private val handler = Handler()
    private val updateIntervalMs = 1000
    val dateFormat = SimpleDateFormat("mm:ss")

    init{
        player.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playWhenReady)
                    startSeekBarUpdate()
                else
                    handler.removeCallbacksAndMessages(null)
            }
            override fun onPositionDiscontinuity(reason: Int) {
                super.onPositionDiscontinuity(reason)
                currentAudio.value = playingPlaylist[player.currentMediaItemIndex]
            }
        })

    }

    private fun startSeekBarUpdate() {
        handler.post(object : Runnable {
            override fun run() {
                playerEventListener?.onPositionChanged(player.currentPosition, player.duration )
                handler.postDelayed(this, updateIntervalMs.toLong())
            }
        })
    }

    fun setPlaylist(list: List<Audio>){
        val userAgent = Util.getUserAgent(context,
            "KateMobileAndroid/92.2 v1-524 (Android 11; SDK 30; x86; Pixel sdk_gphone_x86_arm; ru)")
        val mediaItems = list.map { MediaItem.fromUri(Uri.parse(it.url)) }

        val mediaSource = ConcatenatingMediaSource()

        for (mediaItem in mediaItems) {
            mediaSource.addMediaSource(
                ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context, userAgent))
                    .createMediaSource(mediaItem)
            )
        }
        player.prepare(mediaSource)
    }

    fun playAudioUserSelected(list: List<Audio>, item: Audio){
        player.seekTo(list.indexOf(item), 0)
        player.playWhenReady = true
    }

    fun nextAudio(){
        player.next()
        player.playWhenReady = true
    }

    fun previousAudio(){
        player.previous()
        player.playWhenReady = true
    }

    fun switchPlaying(){
        player.playWhenReady = !player.isPlaying
    }

    fun seekTo(positionSlider: Float) {
        player.seekTo((positionSlider * player.duration).toLong())
    }

    fun getFormatTime(time: Long): String{
        val date = Date(time)
        return dateFormat.format(date)
    }

    fun setPlayerEventListener(listener: PlayerEventListener) {
        playerEventListener = listener
    }

    fun clearMediaItems() {
        player.clearMediaItems()
    }
}