package com.example.appfilm.Model

data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val poster_path: String
){
    constructor() : this("", 0, "", "")
}