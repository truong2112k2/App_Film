package com.example.appfilm.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appfilm.R
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityScreenRegisterBinding
import com.example.appfilm.databinding.AlertDialogRegisterBinding


class ScreenRegister : AppCompatActivity() {
    private val binding by lazy { ActivityScreenRegisterBinding.inflate(layoutInflater) }
    private lateinit var animation: Animation
    private val liveData: liveData by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        animation = AnimationUtils.loadAnimation(this@ScreenRegister, R.anim.animation)
        setUpSpannableString() // tạo hiệu ứng cho chữ đăng nhập
        clickStringLogIn() // chuyển tới màn hình đăng nhập
        registerFuntion() // hàm đăng kí tài khỏan


    }

    @SuppressLint("SuspiciousIndentation")
    private fun registerFuntion() { // hàm đăng kí tài khoản
        binding.btnRegister.setOnClickListener {
            if (isNetWorkAvailable()) { // kiểm tra xem người dùng có đang kết nối wifi hay không
                binding.btnRegister.startAnimation(animation) // xét hiệu ứng khi click nút
                // lấy nội dung từ editext
                liveData.userEmail.value = binding.edtInputEmail.text.toString().trim()
                liveData.userPassword.value = binding.edtInputPassword.text.toString().trim()
                liveData.createAccount(
                    liveData.userEmail.value.toString(),
                    liveData.userPassword.value.toString()
                )  // gọi hàm tạo tài khoản từ liveData
            } else {
                settingWifi() // mở hộp thoại yêu cầu ng dùng bật wifi
            }


        }
        liveData.registerTitle.observe(this@ScreenRegister) { registerTitle ->
            if (registerTitle == "Đăng ký thành công") { // nếu đăng kí thành công
                createSuccessAlert(registerTitle) // hiển thị hộp thoại thông báo thành công
            } else {
                createFailureAlert(registerTitle) // hiển thị hộp thoại thông báo thất bại
            }
        }
    }


    @SuppressLint("SetTextI18n", "ResourceType")
    private fun createFailureAlert(title: String) { // khởi tạo 1 alerdialog thông báo khi thất bại
        val builder = AlertDialog.Builder(this@ScreenRegister)
        val layout = AlertDialogRegisterBinding.inflate(layoutInflater)
        builder.setView(layout.root)
        val alertFailure = builder.create()
        layout.iconAlert.setImageResource(R.drawable.ic_failure)
        layout.txtTitle.text = title
        layout.btnStart.text = "Tạo Lại"
        layout.btnEnd.text = "Thoát"

        layout.btnStart.setOnClickListener { //
            alertFailure.dismiss()
        }
        layout.btnEnd.setOnClickListener {
            alertFailure.dismiss()
            toScreenLogIn()
        }
        alertFailure.show()
        alertFailure.window?.setBackgroundDrawableResource(R.drawable.background_alertdialog) // xét background cho cardview
        alertFailure.setCancelable(false)
    }

    @SuppressLint("SetTextI18n")
    private fun createSuccessAlert(title: String) { // khởi tạo 1 alerdialog thông báo khi thành công
        val builder = AlertDialog.Builder(this@ScreenRegister)
        val layout = AlertDialogRegisterBinding.inflate(layoutInflater)
        builder.setView(layout.root)
        val alertSuccess = builder.create()
        layout.iconAlert.setImageResource(R.drawable.ic_success)
        layout.txtTitle.text = title
        layout.btnStart.text = "Tạo Mới"
        layout.btnEnd.text = "Đăng Nhập"
        layout.btnStart.setOnClickListener {
            alertSuccess.dismiss()
            clearEditText()
        }
        layout.btnEnd.setOnClickListener {
            alertSuccess.dismiss()
            toScreenLogIn()
        }
        alertSuccess.show()
        alertSuccess.window?.setBackgroundDrawableResource(R.drawable.background_alertdialog) // xét background cho alerdialog(khi alerdialog là cardview)
        alertSuccess.setCancelable(false)

    }

    private fun toScreenLogIn() { //trở về màn hình đăng nhập
        finish()
    }

    private fun clickStringLogIn() { // trở về màn hình đăng nhập khi click [Đăng Nhập]
        binding.txtScreenLogIn.setOnClickListener {
            binding.txtScreenLogIn.startAnimation(animation)
            toScreenLogIn()
        }

    }

    private fun clearEditText() { // xóa nội dung trên các editext nhập liệu
        binding.edtInputEmail.setText("")
        binding.edtInputPassword.setText("")
        binding.edtInputEmail.requestFocus()
    }

    fun setUpSpannableString() { // xét hiệu ứng cho chuỗi Đăng Nhập
        val string2 = "Đăng nhập!"
        val stringCRA2 = SpannableString(string2)
        stringCRA2.setSpan(UnderlineSpan(), 0, 10, 1)
        stringCRA2.setSpan(ForegroundColorSpan(Color.BLUE), 0, 10, 1)
        binding.txtScreenLogIn.text = stringCRA2
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