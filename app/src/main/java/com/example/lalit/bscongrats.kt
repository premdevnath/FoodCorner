package com.example.lalit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lalit.databinding.FragmentBscongratsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class bscongrats : BottomSheetDialogFragment() {
  lateinit var binding:FragmentBscongratsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBscongratsBinding.inflate(inflater,container, false)
        binding.gohome.setOnClickListener(){
            var intent= Intent(requireContext(),start1::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}

