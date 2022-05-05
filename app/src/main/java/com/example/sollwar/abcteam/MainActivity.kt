package com.example.sollwar.abcteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appsflyer.AppsFlyerConversionListener
import com.example.sollwar.abcteam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras;
        val afStatus = arguments?.getString("af_status") ?: "null"
        val isFirstLaunch = arguments?.getBoolean("is_first_launch") ?: false
        val installTime = arguments?.getString("install_time") ?: "null"

        if (afStatus == "Organic") {
            binding.afStatus.text = "Выполнена органическая установка приложения"
        } else binding.afStatus.text = "Выполнена неорганическая установка приложения"
        if (isFirstLaunch) {
            binding.firstLaunch.text = "Это первый запуск"
        } else binding.firstLaunch.text = "Это не первый запуск"
        binding.installTime.text = "Дата установки\n$installTime"
    }
}