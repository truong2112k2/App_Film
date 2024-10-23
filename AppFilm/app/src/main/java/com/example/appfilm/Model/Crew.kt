package com.example.appfilm.Model

data class Crew(
    val adult: Boolean,                  // Xác định liệu thành viên của ekip có tham gia sản xuất phim người lớn hay không (true = phim người lớn).
    val credit_id: String,               // ID đặc biệt cho sự tham gia của thành viên ekip trong bộ phim, dùng để theo dõi credit.
    val department: String,              // Bộ phận mà thành viên ekip làm việc (ví dụ: "Directing", "Production").
    val gender: Int,                     // Giới tính của thành viên ekip (1 = nữ, 2 = nam, 0 = không xác định).
    val id: Int,                         // ID duy nhất của thành viên ekip trong cơ sở dữ liệu.
    val job: String,                     // Vai trò công việc cụ thể của thành viên ekip trong bộ phim (ví dụ: "Director", "Producer").
    val known_for_department: String,    // Bộ phận công việc nổi bật mà thành viên ekip được biết đến (ví dụ: "Directing").
    val name: String,                    // Tên đầy đủ của thành viên ekip.
    val original_name: String,           // Tên thật của thành viên ekip (nếu khác với `name`).
    val popularity: Double,              // Mức độ nổi tiếng của thành viên ekip dựa trên số liệu đo đạc (ví dụ như mức độ tìm kiếm).
    val profile_path: String             // Đường dẫn đến hình ảnh đại diện của thành viên ekip trên hệ thống (thường là URL).
)
