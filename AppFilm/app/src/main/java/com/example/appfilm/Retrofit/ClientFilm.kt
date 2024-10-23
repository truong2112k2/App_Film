package com.example.appfilm.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientFilm {
   private const val baseUrl = "https://api.themoviedb.org/3/"


    val instance: ApiService by lazy {
      val retrofit = Retrofit.Builder()
         .baseUrl(baseUrl)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
      retrofit.create(ApiService :: class.java)
   } // gọi dữ liệu từ theFilmDB

    val intanceVideoFromYoutube: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    } // lấy video từ youtube


}