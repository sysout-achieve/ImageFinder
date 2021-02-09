package com.gunt.imagefinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gunt.imagefinder.GlobalExceptionHandler
import com.gunt.imagefinder.R
import com.gunt.imagefinder.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    setContentView(binding.root)

    GlobalExceptionHandler.setActivity(this)
  }
}
