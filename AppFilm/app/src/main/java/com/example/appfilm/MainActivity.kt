package com.example.appfilm

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appfilm.Screen.ScreenLogIn
import com.example.appfilm.ViewModel.liveData
import com.example.appfilm.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
   private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
   private val liveData: liveData by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        toScreenLogIn() // hàm chuyển đến màn hình đăng nhập
    }
    private fun toScreenLogIn() {
        lifecycleScope.launch {
          delay(3000)
            withContext(Dispatchers.Main){
                startActivity(Intent(this@MainActivity, ScreenLogIn:: class.java))
                finish()
            }
        }
    }
}