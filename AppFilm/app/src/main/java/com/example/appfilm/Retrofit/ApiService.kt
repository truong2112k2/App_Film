package com.example.appfilm.Retrofit

import com.example.appfilm.Model.ModelActorFilm
import com.example.appfilm.Model.ModelFilmDetail
import com.example.appfilm.Model.ModelFilmPopular
import com.example.appfilm.Model.ModelVideoFilm
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular?")
   suspend fun getDataFilmPopular(
    @Query("api_key") api_key: String,
    @Query("language") language: String,
    @Query("page") page: Int,
    @Query("sort_by") sort: String,
   ): ModelFilmPopular // lấy danh sách các bộ phim phổ biến

    @GET("movie/now_playing?")
    suspend fun getDataFilmNowPlaying(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort: String,
    ): ModelFilmPopular // lấy danh sách các bộ phim đang chiếu

    @GET("movie/upcoming?")
    suspend fun getDataFilmUpComing(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("sort_by") sort: String,
    ): ModelFilmPopular// lấy danh sách các bộ phim sắp chiếu



    @GET("movie/top_rated?")
   suspend fun getDataFilmHighestRate(
       @Query("api_key") api_key: String,
       @Query("language") language: String,
       @Query("page") page: Int,
       @Query("sort_by") sort: String,
   ): ModelFilmPopular// lấy danh sách các bộ phim được đánh giá cao

    @GET("movie/{movie_id}")
    fun getFilmDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<ModelFilmDetail> // lấy thông tin chi tiết bộ phim

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,// "movie_id" là phần thay thế trong URL nên nó phải là path
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<ModelActorFilm> // lấy danh sách các diễn viên của bộ phim


    @GET("search")
    fun getVideoFilm(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 3,
        @Query("key") apiKey: String
    ): Call<ModelVideoFilm> // lấy các video liên quan đến bộ phim, từ youtube


    //https://api.themoviedb.org/3/search/movie?
// api_key=3622cd6028fc0adb19958b001355666a
// &language=vi-VN&
// query=cu%E1%BB%99c%20chi%E1%BA%BFn%20v%C3%B4%20c%E1%BB%B1c
    @GET("search/movie")
    fun searchFilmWithName(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,

    ): Call<ModelFilmPopular> // tìm kiếm bộ phim theo tên
}