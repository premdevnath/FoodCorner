package com.example.lalit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lalit.databinding.ActivityMainBinding
import com.example.lalit.databinding.ActivityRagisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth


    }
}