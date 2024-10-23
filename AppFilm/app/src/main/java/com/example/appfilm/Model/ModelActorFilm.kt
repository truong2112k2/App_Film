package com.example.appfilm.Model

// model diễn viên các bộ phim
data class ModelActorFilm(
    val cast: List<Cast>, // danh sách diễn viên
    val crew: List<Crew>, // danh sách đoàn làm phim
    val id: Int // id
)