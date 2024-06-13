package com.example.lalit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.lalit.databinding.ActivityLocationBinding

class location : AppCompatActivity() {
    lateinit var binding: ActivityLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var datalist= listOf<String>("jaipur","Bhilwara","udaipur","kota","ajmera","sieol")
        var adapter=ArrayAdapter(this,android.R.layout.simple_spinner_item,datalist)
        binding.spinner.adapter=adapter
        binding.spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
              var sp=parent?.getItemAtPosition(position)
                Toast.makeText(this@location, "${sp}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }
}