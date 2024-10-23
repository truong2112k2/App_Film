package com.example.appfilm.Model

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
){
    constructor() : this("", "")
}