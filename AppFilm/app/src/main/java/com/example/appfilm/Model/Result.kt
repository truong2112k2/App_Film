package com.example.appfilm.Model

data class Result(
    val adult: Boolean,
    val backdrop_path: String, // link ảnh
    val genre_ids: List<Int>, // danh sách thể loại
    val id: Int, // mã phim
    val original_language: String, // ngôn ngữ ban đầu
    val original_title: String, // tiêu đề ban đầu
    val overview: String, // miêu tả ngắn
    val popularity: Double,// độ phổ biến
    val poster_path: String,
    /*
     Đường dẫn đến ảnh poster của phim. Tương tự như backdrop_path, bạn cần ghép với URL cơ bản như sau: https://image.tmdb.org/t/p/w500/lfY2CfmxyN9OvxmFuap6aejViJn.jpg để hiển thị ảnh.
     */
    val release_date: String, // ngày ra mắt
    val title: String, // tên phim
    val video: Boolean, // phim có p video hay k
    val vote_average: Double, // điểm trung bình
    val vote_count: Int // số lụợng bình trọn
)