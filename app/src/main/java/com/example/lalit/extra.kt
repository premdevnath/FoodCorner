package com.example.lalit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lalit.databinding.ActivityExtraBinding

class extra : AppCompatActivity() {
    lateinit var binding:ActivityExtraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExtraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent= Intent(this,login::class.java)
        startActivity(intent)

    }
}