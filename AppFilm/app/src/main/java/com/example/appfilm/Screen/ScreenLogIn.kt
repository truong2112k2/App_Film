package com.example.appfilm.Screen

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.example.appfilm.R
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityScreenLogInBinding
import com.google.android.material.snackbar.Snackbar

class ScreenLogIn : AppCompatActivity() {
    private val binding by lazy { ActivityScreenLogInBinding.inflate(layoutInflater) }
    private lateinit var animation: Animation
    private val liveData: liveData by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        animation = AnimationUtils.loadAnimation(this@ScreenLogIn, R.anim.animation)
        setUpSpannableString() // tạo hiệu ứng cho chữ quên mật khẩu và đăng ký ta khoản
        logInFuntion()/// bật hàm này lên để bật các chức năng đăng nhập, đăng kí
        toScreenRegister() // chuyển đếm màn hình đăng ký tài khoản
        toScreenResetPasword() // chuyển tới màn hình quên mật khẩu
 


    }


    private fun logInFuntion() { // đăng nhập tài khoản
        binding.btnLogIn.setOnClickListener { // khi người dùng click nút đăng nhập
            binding.btnLogIn.startAnimation(animation) // xét hiệu ứng cho nút
            if (isNetWorkAvailable()) { // nếu máy người dùng có kết nối wifi
                //lấy thông tin từ edtInputEmail và edtInputPassword và gán vào liveData
                liveData.userEmail.value = binding.edtInputEmail.text.toString().trim()
                liveData.userPassword.value = binding.edtInputPassword.text.toString().trim()
                // gọi hàm logIn từ liveData để kiểm tra đăng nhập tài khoản
                liveData.logInAccount(
                    liveData.userEmail.value.toString(),
                    liveData.userPassword.value.toString()
                )
            } else { // nếu không có wifi
                settingWifi() // mở hộp thoại yêu cầu kết nối wifi
            }
        }
        // gọi biến loginTitle từ liveData để quan sát kết quả kiểm tra tài khoản
        liveData.loginTitle.observe(this@ScreenLogIn) { logInTitle ->
            if (logInTitle == "Đăng nhập thành công") {
                // chuyển tới màn hình Home nếu đăng nhập thành công
                createSnackbar(logInTitle)
             ///  liveData.idUser.observe(this@ScreenLogIn){
                   toScreenHome("ok")
           //   }
            } else {
                createSnackbar(logInTitle)
            }
        }
    }

    private fun toScreenHome(idUser: String) {
        val intent = Intent(this@ScreenLogIn, ScreenHome::class.java)
        intent.putExtra("id_user", idUser)
        startActivity(intent)
        finish()
    }

    private fun toScreenRegister() { // chuyển đến screen đăng ký
        binding.txtCreateAccount.setOnClickListener {
            binding.txtCreateAccount.startAnimation(animation)
            startActivity(Intent(this@ScreenLogIn, ScreenRegister::class.java))
        }
    }


    fun setUpSpannableString() { // tạo hiệu ứng cho chữ quên mật khẩu và đăng ký tài khoản
        val string1 = "Quên mật khẩu?"
        val string2 = "Tạo ngay"
        val stringFGP = SpannableString(string1)
        stringFGP.setSpan(UnderlineSpan(), 0, 14, 1)
        val stringCRA = SpannableString(string2)
        stringCRA.setSpan(UnderlineSpan(), 0, 8, 1)
        stringCRA.setSpan(ForegroundColorSpan(Color.BLUE), 0, 8, 1)
        binding.txtForgetPassword.text = stringFGP
        binding.txtCreateAccount.text = stringCRA
    }


    private fun toScreenResetPasword() {
        binding.txtForgetPassword.setOnClickListener {
            binding.txtForgetPassword.startAnimation(animation) // xét hiệu ứng cho chữ quên mật khẩu
            val intent = Intent(this@ScreenLogIn, ScreenResetPassword::class.java)
            startActivity(intent)
        }

    }



    private fun createSnackbar(title: String) { // tạo snackbar hiển thị thông báo
        val snackbar = Snackbar.make(binding.root, title, Toast.LENGTH_SHORT)
        snackbar.show()
    }

    private fun isNetWorkAvailable(): Boolean { // kiểm tra kết nối wifi
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun settingWifi() { // mở cài đặt wifi
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Bạn đang mất kết nối")
            .setMessage("Kiểm tra kết nối ngay?")
            .setPositiveButton("[Ok]") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("[Trở về]") { dialogInterface: DialogInterface, i: Int ->
                finish()
            }
        alertDialog.show()
    }

}
