package com.example.appfilm.Screen

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appfilm.R
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityScreenResetPasswordBinding

class ScreenResetPassword : AppCompatActivity() {
    private val binding by lazy { ActivityScreenResetPasswordBinding.inflate(layoutInflater) }
    private val liveData: liveData by viewModels()
    lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        animation = AnimationUtils.loadAnimation(this@ScreenResetPassword, R.anim.animation)
        resetPasswordFuntion()
    }

    private fun resetPasswordFuntion() {
        binding.btnResetPassword.setOnClickListener {
            binding.btnResetPassword.startAnimation(animation)
            liveData.userEmail.value = binding.edtInputEmailReset.text.toString().trim()
            liveData.resetPassword(liveData.userEmail.value.toString())
        }
        liveData.resetPasswordTitle.observe(this@ScreenResetPassword) {
            Toast.makeText(this@ScreenResetPassword, "${it}", Toast.LENGTH_SHORT).show()

        }
    }
}