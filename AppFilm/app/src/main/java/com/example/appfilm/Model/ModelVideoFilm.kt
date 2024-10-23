package com.example.appfilm.Model

// model video lấy từ youtube
data class ModelVideoFilm(
    val items: List<VideoItem>
)
data class VideoItem(
    val id: VideoId
)

data class VideoId(
    val videoId: String
)