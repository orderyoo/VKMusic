package com.example.musik

import com.google.gson.annotations.SerializedName

data class Audios(
    val response: ResponseAudio
)

data class ResponseAudio(
    val count: Int,
    val items: List<Audio>
)

data class Audio(
    val artist: String,
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int,
    val accessKey: String,
    val ads: Ads,
    val isExplicit: Boolean,
    val isFocusTrack: Boolean,
    val isLicensed: Boolean,
    val trackCode: String,
    val url: String,
    val date: Long,
    val mainArtists: List<MainArtist>,
    val shortVideosAllowed: Boolean,
    val storiesAllowed: Boolean,
    val storiesCoverAllowed: Boolean
)

data class Ads(
    val contentId: String,
    val duration: String,
    val accountAgeType: String,
    val puid1: String,
    val puid22: String
)

data class MainArtist(
    val name: String,
    val domain: String,
    val id: String,
    @SerializedName("is_followed")
    val isFollowed: Boolean,
    @SerializedName("can_follow")
    val canFollow: Boolean
)






