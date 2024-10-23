package com.example.appfilm.Model
// model phim phổ biến
data class ModelFilmPopular(
    val page: Int, // số trang
    val results: List<Result>, // kết quả
    val total_pages: Int, // tổng số trang
    val total_results: Int // tổng số kết quả
     )