package com.example.musik


interface PlayerEventListener {
    fun onPositionChanged(currentPosition: Long, duration: Long)
}

interface Observer{
    fun onEdit()
}