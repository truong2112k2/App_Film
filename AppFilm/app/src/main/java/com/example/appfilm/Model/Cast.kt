package com.example.appfilm.Model

data class Cast(
    val adult: Boolean,
    // Xác định liệu diễn viên này có đóng phim dành cho người lớn hay không (true = phim người lớn).
    val cast_id: Int,
    // ID của diễn viên trong danh sách casting của bộ phim.
    val character: String,
    // Tên của nhân vật mà diễn viên đóng trong bộ phim.
    val credit_id: String,
    // ID đặc biệt cho sự tham gia của diễn viên trong bộ phim, dùng để theo dõi credit.
    val gender: Int,
    // Giới tính của diễn viên (1 = nữ, 2 = nam, 0 = không xác định).
    val id: Int,
    // ID của diễn viên (ID duy nhất trong cơ sở dữ liệu).
    val known_for_department: String,
    // Bộ phận công việc nổi bật mà diễn viên được biết đến (ví dụ: "Acting").
    val name: String,
    // Tên đầy đủ của diễn viên.
    val order: Int,
    // Thứ tự của diễn viên trong danh sách casting (thường theo mức độ quan trọng của vai diễn).
    val original_name: String,
    // Tên thật của diễn viên, không phải nghệ danh (nếu khác với `name`).
    val popularity: Double,
    // Mức độ nổi tiếng của diễn viên dựa trên số liệu đo đạc (ví dụ như mức độ tìm kiếm).
    val profile_path: String
  // Đường dẫn đến hình ảnh đại diện của diễn viên trên hệ thống (thường là URL).
)
