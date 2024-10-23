package com.example.appfilm.Model

data class Genre(
    val id: Int,
    val name: String
){
    constructor() : this(0, "")
}