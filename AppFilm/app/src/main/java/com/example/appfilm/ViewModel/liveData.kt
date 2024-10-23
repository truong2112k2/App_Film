package com.example.appfilm.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DecimalFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.Model.ModelActorFilm
import com.example.appfilm.Model.ModelFilmDetail
import com.example.appfilm.Model.ModelFilmPopular
import com.example.appfilm.Model.ModelLoveFilm
import com.example.appfilm.Model.ModelVideoFilm
import com.example.appfilm.Retrofit.ClientFilm
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Pattern

class liveData : ViewModel() {

    val userEmail = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val registerTitle = MutableLiveData<String>()
    val loginTitle = MutableLiveData<String>()
    val resetPasswordTitle = MutableLiveData<String>()
    val firebaseAuthentication = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()


    val checkResult = MutableLiveData<Boolean>()

    val nameFilm = MutableLiveData<String>()
    val idFilm = MutableLiveData<String>() // mã phim
    val genreFilm: MutableLiveData<String> by lazy { // thể loại phim
        MutableLiveData<String>().also {
            it.value = "Thể loại: "
        }
    }


    val spokenLanguage: MutableLiveData<String> by lazy { // các ngôn ngữ trong phim
        MutableLiveData<String>().also {
            it.value = "Ngôn ngữ trong phim: "
        }
    }

    val countryFilm: MutableLiveData<String> by lazy { // các quốc gia sản xuất
        MutableLiveData<String>().also {
            it.value = "Quốc gia: "
        }
    }


    val addLoveFilmTitle = MutableLiveData<String>() // kết quả thêm 1 bộ phim yêu thuchs
    val listFilmSearch = MutableLiveData<ModelFilmPopular>() // danh sách phim tìm kiếm theo tên
    val listFilmPopular = MutableLiveData<ModelFilmPopular>() // danh sách phim phổ biết

    val listFilmHighestRate = MutableLiveData<ModelFilmPopular>() // danh sách phim phổ biết
    val listActorFilm = MutableLiveData<ModelActorFilm>() // danh sách diễn viên trong film
    val listVideoFilm = MutableLiveData<ModelVideoFilm>() // danh sách video liên quan đến bộ phim
    val listFilmPlayingNow = MutableLiveData<ModelFilmPopular>() // danh sách các bộ phim đang chiếu ở thời điểm hiện tại
    val listFilmUpComing = MutableLiveData<ModelFilmPopular>() // danh sách các bộ phim sắp chiếu
    val detailFilm = MutableLiveData<ModelFilmDetail>() // thông tin chi tiết phim
    val clientFilm = ClientFilm
    val api_key = "3622cd6028fc0adb19958b001355666a" // api_key của theFilmDB
    val api_key_youtube = "AIzaSyCfz2ZGO59aBX7VUDeN6nC47DH4h0oX94M" // api_key youtube
    val language = "vi-VN" // ngôn ngữ
    val sort = "popularity.desc" // kiểu sắp xếp

    val idUser = MutableLiveData<String>()
    /// firebaseAuthentication.currentUser!!.uid

    /* Lấy dữ liệu từ API ----------------------------------- */
//
    fun getListSearchFilm(nameFilm: String) {
        try {
            viewModelScope.launch {
                clientFilm.instance.searchFilmWithName(api_key, language, nameFilm)
                    .enqueue(object : Callback<ModelFilmPopular> {
                        override fun onResponse(
                            call: Call<ModelFilmPopular>,
                            response: Response<ModelFilmPopular>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()
                                data.let { dataFilm ->
                                    listFilmSearch.value = dataFilm!!
                                }
                            }

                        }

                        override fun onFailure(call: Call<ModelFilmPopular>, t: Throwable) {
                            Log.d("ERROR_SEARCH_FILM_FAILURE", t.toString())
                        }
                    })
            }
        } catch (e: Exception) {
            Log.d("ERROR_SEARCH_FILM_FAILURE", e.toString())

        }
    }


    fun getListVideoFilm(name: String) { // lấy list mã của các video liên quan đến bộ phim
        try {
            viewModelScope.launch(Dispatchers.IO) {
                clientFilm.intanceVideoFromYoutube.getVideoFilm(
                    query = name,
                    apiKey = api_key_youtube
                ).enqueue(object : Callback<ModelVideoFilm> {
                    override fun onResponse(
                        call: Call<ModelVideoFilm>,
                        response: Response<ModelVideoFilm>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            listVideoFilm.value =
                                data!! // gán kết quả vào biến listVideoFilm để hiển thị video lên giao diện
                        }
                    }

                    override fun onFailure(call: Call<ModelVideoFilm>, t: Throwable) {
                        Log.d("ERROR_VIDEO_FILM_FAILURE", t.toString())
                    }
                })
            }

        } catch (e: Exception) {
            Log.d("ERROR_VIDEO_FILM_FAILURE", e.toString())
        }
    }

    fun getListActorFilm(id: Int) { // lấy danh sách diễn viên
        try {
            viewModelScope.launch {
                clientFilm.instance.getMovieCredits(id, api_key, language)
                    .enqueue(object : Callback<ModelActorFilm> {
                        override fun onResponse(
                            call: Call<ModelActorFilm>,
                            response: Response<ModelActorFilm>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()
                                data.let {
                                    listActorFilm.value = it
                                }
                            }
                        }

                        override fun onFailure(call: Call<ModelActorFilm>, t: Throwable) {
                            Log.d("ERROR_DATA_FILM_ACTOR_FAILURE", t.toString())
                        }
                    })
            }
        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_ACTOR_FAILURE", e.toString())

        }

    }

    fun getDetailFilmWithID(id: Int) { // lấy thông tin chi tiết phim qua ID
        try {
            viewModelScope.launch {
                clientFilm.instance.getFilmDetails(id, api_key, language)
                    .enqueue(object : Callback<ModelFilmDetail> {
                        override fun onResponse(
                            call: Call<ModelFilmDetail>,
                            response: Response<ModelFilmDetail>
                        ) {

                            if (response.isSuccessful) {
                                val dataDetail = response.body()
                                dataDetail.let { dataFilm ->
                                    detailFilm.value = dataFilm!!
                                }
                            }
                        }

                        override fun onFailure(call: Call<ModelFilmDetail>, t: Throwable) {
                            Log.d("ERROR_DATA_FILM_DETAIL_FAILURE", t.toString())
                        }
                    })

            }
        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_DETAIL_FAILURE", e.toString()) //in lỗi ra log để theo dõi
        }
    }

    fun getListFilmPopular(page: Int) { // lấy danh sách phim phổ biến
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val data = clientFilm.instance.getDataFilmPopular(api_key, language, page, sort)
                withContext(Dispatchers.Main) {
                    listFilmPopular.value = data
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_POPULAR_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }
    }

    fun getListHighestRateFilm(page: Int) { // lấy các bộ phim phổ biến
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val data = clientFilm.instance.getDataFilmHighestRate(api_key, language, page, sort)
                withContext(Dispatchers.Main) {
                    listFilmHighestRate.value = data
                }
            }

        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_HIGHEST_RATE_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }
    }

    fun getListFilmPlayingNow(page: Int) { // lấy các bộ phim đang được chiếu
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val data = clientFilm.instance.getDataFilmNowPlaying(api_key, language, page, sort)
                withContext(Dispatchers.Main) {
                    listFilmPlayingNow.value = data
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_PLAYING_NOW_FAILURE", e.toString()) //in lỗi ra log để theo dõi
        }

    }

    fun getListFilmUpComing(page: Int) {// lấy các bộ phim sắp được chiếu
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val data = clientFilm.instance.getDataFilmUpComing(api_key, language, page, sort)
                withContext(Dispatchers.Main) {
                    listFilmUpComing.value = data
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_DATA_FILM_UP_COMING_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }
    }
    /* Đăng nhập, đăng ký, lấy lại mật khẩu tài khoản----------------------------------- */

    fun getIdUser(): String {
        idUser.value = firebaseAuthentication.currentUser!!.uid
        val id = idUser.value.toString()
        return id
    }

    // đăng nhập tài khoản
    fun logInAccount(email2: String, password2: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                if (checkLengthString(email2) || checkLengthString(password2)) {
                    withContext(Dispatchers.Main) {
                        loginTitle.value = "Nội dung bị bỏ trống"
                    }
                } else {
                    firebaseAuthentication.signInWithEmailAndPassword(email2, password2)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                loginTitle.value = "Đăng nhập thành công"
                            } else {
                                val e = it.exception
                                loginTitle.value = "Tài khoản không tồn tại !"
                                Log.d(
                                    "ERROR_LOGIN_FAILURE",
                                    e!!.message.toString()
                                ) //in lỗi ra log để theo dõi

                            }
                        }
                        .addOnFailureListener {
                            Log.d(
                                "ERROR_LOGIN_FAILURE",
                                it.message.toString()
                            ) //in lỗi ra log để theo dõi

                        }


                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_LOGIN_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }
    }

    // đăng ký tài khoản
    fun createAccount(email2: String, password2: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                if (checkLengthString(email2) || checkLengthString(password2)) { // kiểm tra xem email và mật khẩu có bị bỏ trống không
                    withContext(Dispatchers.Main) { // các thao tác cập nhật dữ liệu phải được thực hiện ở Main thread-> nếu không sẽ báo lỗi
                        registerTitle.value =
                            "Nội dung bị bỏ trống" // gán thông báo vào biến registerTitle để quan sát
                    }
                } else if (checkFormEmail(email2)) { // kiểm tra xem email có sai định dạng không
                    withContext(Dispatchers.Main) { // các thao tác cập nhật dữ liệu phải được thực hiện ở Main thread-> nếu không sẽ báo lỗi
                        registerTitle.value =
                            "Email không hợp lệ" // gán thông báo vào biến registerTitle để quan sát
                    }
                } else if (password2.length < 8) { // kiểm tra xem mật khẩu có ít hơn 8 kí tự không
                    withContext(Dispatchers.Main) { // các thao tác cập nhật dữ liệu phải được thực hiện ở Main thread-> nếu không sẽ báo lỗi
                        registerTitle.value =
                            "Mật khẩu phải có ít nhất 8 kí tự" // gán thông báo vào biến registerTitle để quan sát
                    }
                } else if (checkContentPassword(password2)) {
                    withContext(Dispatchers.Main) {
                        registerTitle.value = "Mật khẩu phải gồm cả chữ và số"
                    }
                } else { //  nếu các điều kiện đều đạt -> thực hiện đăng ký tài khoản trên firebase authentication
                    firebaseAuthentication.createUserWithEmailAndPassword(email2, password2)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                registerTitle.value = "Đăng ký thành công"
                            }
                        }
                        .addOnFailureListener { error ->
                            // Đây là nơi in ra các lỗi, firebase authentication có hỗ trợ điều này, nó hỗ trợ 1 số lỗi như : sai định dạng, mật khẩu yếu..
                            // Tuy nhiên các lỗi trên hầu như đã được xử lý bằng các hàm viết riêng, nên gần như chỉ có mình lỗi trùng lặp email là sẽ được in ra ở đây
                            registerTitle.value = "Email đã tồn tại"
                            Log.d(
                                "ERROR_REGISTER_FAILURE",
                                error.toString()
                            ) // in lỗi ra log để theo dõi
                        }
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_REGISTER_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }
    }

    /// reset mật khẩu

    @SuppressLint("SuspiciousIndentation")
    fun resetPassword(email: String) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                if (checkLengthString(email)) { // kiểm tra xem email có bị bỏ trống không
                    withContext(Dispatchers.Main) {
                        resetPasswordTitle.value = "Nội dung bị bỏ trống"
                    }
                } else if (checkFormEmail(email)) {// kiểm tra xem email có đúng định dạng không
                    withContext(Dispatchers.Main) {
                        resetPasswordTitle.value = "Email sai định dạng"
                    }
                } else {
                    firebaseAuthentication.sendPasswordResetEmail(email) //// gửi mail reset password đến người dùng
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                resetPasswordTitle.value = "Gửi mail thành công"
                                Log.d(
                                    "ERROR_CHECKEMAIL_FAILURE",
                                    task.exception?.message.toString()
                                ) //in lỗi ra log để theo dõi

                            } else {
                                resetPasswordTitle.value = "Gửi mail thất bại"


                            }
                        }
                }
            }
        } catch (e: Exception) {
            Log.d("ERROR_CHECKEMAIL_FAILURE", e.toString()) //in lỗi ra log để theo dõi
        }

    }

    //// Hàm thực hiện thao tác yêu thích bộ phim
    fun addLoveFilm(nodeName: String, filmPopular: ModelFilmDetail) {
        try {
            viewModelScope.launch {
                val firebase = firebaseDatabase.getReference(nodeName)
                val id = firebase.push().key
                val dataLoveFilm = ModelLoveFilm(id, filmPopular)
                firebase.child(id.toString()).setValue(dataLoveFilm)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            addLoveFilmTitle.value = "Thêm phim thành công"
                        }else{
                            addLoveFilmTitle.value = "Thêm phim thất bại"
                        }
                    }
                    .addOnFailureListener {
                        addLoveFilmTitle.value = "Thêm phim thất bại"
                        Log.d(
                            "ERROR_ADD_LOVE_FILM_FAILURE",
                            it.message.toString()
                        ) //in lỗi ra log để theo dõi

                    }
            }
        } catch (e: Exception) {
            Log.d("ERROR_ADD_LOVE_FILM_FAILURE", e.toString()) //in lỗi ra log để theo dõi

        }

    }



    // hàm kiểm tra định dạng email
    fun checkFormEmail(email: String): Boolean {
        // Biểu thức chính quy kiểm tra định dạng email
        val form = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        // tạo 1 biến để biên dịch một biểu thức chính quy trong biến form thành một đối tượng có thể sử dụng để kiểm tra chuỗi.
        val testEmail = Pattern.compile(form)
        // tạo 1 biến sử dụng biểu thức chính quy để kiểm tra xem một chuỗi có khớp với biểu thức đó hay không.
        // so sánh email xem có giống kiểu nhập với testEmail không bằng biểu thức Matcher
        val doTest = testEmail.matcher(email)
        if (doTest.matches()) { // nếu hàm này trả về true -> nghĩa là định dạng của email giống với định dạng mà t quy định tại email
            return false
        } else {
            return true
        }
    }

    fun checkLengthString(string: String): Boolean { // kiểm tra xem chuỗi có bị bỏ trống không
        if (string.length == 0) {
            return true
        } else {
            return false
        }
    }

    fun checkContentPassword(string: String): Boolean { // kiểm tra nội dung mật khẩu có đủ cả chữ và số không
        var text = 0
        var number = 0
        val listString = string.toList()
        for (i in listString) {
            if (i.isDigit()) {
                text++
            } else if (i.isLetter()) {
                number++
            }
        }
        if (number > 0 && text > 0) {
            return false
        } else {
            return true
        }
    }

    fun reverseDateString(dateString: String): String {
        // Tách chuỗi dựa trên dấu "-"
        val parts = dateString.split("-")

        // Đảo ngược chuỗi theo định dạng ngày-tháng-năm
        return "${parts[2]}-${parts[1]}-${parts[0]}"
    }

    fun forMatNumber(string: Double): String {
        val decimalFormat = DecimalFormat("#.0")
        return decimalFormat.format(string)
    }

    fun formatCurrencyUSD(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        return format.format(amount)
    }

    fun forMatGender(number: Int): String {
        when (number) {
            1 -> return "Nữ"
            2 -> return "Nam"
            else -> return "Không rõ"
        }
    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    fun createASnackBar(title: String, context: Context, view: View) {
        val snackBar = Snackbar.make(view, title, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }


}