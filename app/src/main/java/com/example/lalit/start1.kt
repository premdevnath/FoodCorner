package com.example.lalit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lalit.Fragment.bsnoti
import com.example.lalit.databinding.ActivityStart1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class start1 : AppCompatActivity() {
lateinit var binding: ActivityStart1Binding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        binding= ActivityStart1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //r1 hame jis fragment ko pahle start1.xml me show karana hai uski id navcontroller me dali
     var NavController=findNavController(R.id.home)
     //   var bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView)[bindin me is line ko likhne ki jarurat nhi]

        //r2 or bottmnavigation me use set kar diya
      binding.bottomNavigationView.setupWithNavController(NavController)

    //notification butoon

    binding.notification.setOnClickListener() {
        var bottomSheetDialog = bsnoti()
        bottomSheetDialog.show(supportFragmentManager, "test2")
    }




    }
}