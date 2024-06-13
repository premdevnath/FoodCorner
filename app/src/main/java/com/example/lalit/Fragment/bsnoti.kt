package com.example.lalit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lalit.R
import com.example.lalit.adapter
import com.example.lalit.databinding.FragmentBsnotiBinding
import com.example.lalit.notiadapter
import com.example.lalit.notimodal
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class bsnoti : BottomSheetDialogFragment() {

    lateinit var binding:FragmentBsnotiBinding
    lateinit var adapter: notiadapter
    lateinit var notilist:ArrayList<notimodal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBsnotiBinding.inflate(inflater, container, false)
        notilist=ArrayList<notimodal>()
        notilist.add(notimodal(R.drawable.lock_01,"lock your device"))
        notilist.add(notimodal(R.drawable.lock_01,"lock your device"))
        notilist.add(notimodal(R.drawable.lock_01,"lock your device"))
        adapter= notiadapter(notilist)
        binding.nrvi.adapter=adapter
        binding.nrvi.layoutManager=LinearLayoutManager(requireContext())

        return binding.root
    }

    companion object{

    }
}