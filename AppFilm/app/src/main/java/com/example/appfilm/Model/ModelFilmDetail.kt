package com.example.appfilm.Model

// model thông tin chi tiết phim
data class ModelFilmDetail(
    val adult: Boolean, // Xác định phim có nội dung người lớn hay không
    val backdrop_path: String, // Đường dẫn đến ảnh nền của phim
    val belongs_to_collection: BelongsToCollection?, // Phim có thuộc một bộ sưu tập nào không
    val budget: Int, // Ngân sách sản xuất phim (đơn vị: USD)
    val genres: List<Genre>, // Danh sách các thể loại phim
    val homepage: String, // URL của trang web chính thức của phim
    val id: Int, // ID duy nhất của phim trên TMDb
    val imdb_id: String?, // ID của phim trên IMDb (nếu có)
    val origin_country: List<String>, // Danh sách các quốc gia sản xuất phim
    val original_language: String, // Ngôn ngữ gốc của phim
    val original_title: String, // Tiêu đề gốc của phim
    val overview: String, // Nội dung tóm tắt của phim
    val popularity: Double, // Chỉ số phổ biến của phim
    val poster_path: String, // Đường dẫn đến poster của phim
    val production_companies: List<ProductionCompany>, // Danh sách các công ty sản xuất phim
    val production_countries: List<ProductionCountry>, // Danh sách các quốc gia sản xuất phim
    val release_date: String, // Ngày phát hành của phim
    val revenue: Long, // Doanh thu của phim (đơn vị: USD)
    val runtime: Int, // Thời lượng phim (đơn vị: phút)
    val spoken_languages: List<SpokenLanguage>, // Danh sách các ngôn ngữ được nói trong phim
    val status: String, // Trạng thái phát hành của phim (ví dụ: Released, Post Production)
    val tagline: String?, // Slogan hoặc câu tagline của phim (nếu có)
    val title: String, // Tiêu đề phim (có thể khác với tiêu đề gốc)
    val video: Boolean, // Xác định phim có video liên quan hay không
    val vote_average: Double, // Điểm đánh giá trung bình của phim
    val vote_count: Int // Tổng số lượt đánh giá của phim
){
    // Constructor không tham số (mặc định)
    constructor() : this(
        adult = false,
        backdrop_path = "",
        belongs_to_collection = null,
        budget = 0,
        genres = emptyList(),
        homepage = "",
        id = 0,
        imdb_id = null,
        origin_country = emptyList(),
        original_language = "",
        original_title = "",
        overview = "",
        popularity = 0.0,
        poster_path = "",
        production_companies = emptyList(),
        production_countries = emptyList(),
        release_date = "",
        revenue = 0L,
        runtime = 0,
        spoken_languages = emptyList(),
        status = "",
        tagline = null,
        title = "",
        video = false,
        vote_average = 0.0,
        vote_count = 0
    )
}
