package com.example.lalit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lalit.databinding.FragmentMenubheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//[bottom sheet yani screen ke bottom ye ek fragment ya ek activity niklegi]
//r*1[ye bottaomshet bfhome me viewmenu(textview) par click karne par niche se aayega]
class Menubheet : BottomSheetDialogFragment(){
    lateinit var binding:FragmentMenubheetBinding
    lateinit var bsadapter: bsadapter
    lateinit var bslistdata:ArrayList<Bsmodal>
    lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMenubheetBinding.inflate(inflater ,container, false)


        //[yaha menu par back button laagaya jise dabane par ye dismis ho jayeha]
        binding.back.setOnClickListener(){
           dismiss()
        }

        //***r1 [data base se data retrive](firebase data retrive)
        bslistdata=ArrayList<Bsmodal>()
        database=FirebaseDatabase.getInstance()
        retrievdata()
        return binding.root
    }

    //yaha same vo hi kiay jo lalitp me kiya tha
    //r2[](r3 adapter me hai)
    private fun retrievdata() {
     val foodref=database.reference.child("menu")

         foodref.addListenerForSingleValueEvent(object :ValueEventListener {
             override fun onDataChange(snapshot: DataSnapshot) {
                bslistdata.clear()
                 for (bsanpshot in snapshot.children)
                 {
                     var menuitem=bsanpshot.getValue(Bsmodal::class.java)
                     menuitem.let {
                         bslistdata.add(it!!)
                     }
                 }
                 if(bslistdata.isNotEmpty()) {
                     bsadapter = bsadapter(bslistdata, requireContext())
                     binding.bsrvi.adapter = bsadapter
                     binding.bsrvi.layoutManager = LinearLayoutManager(requireContext())
                 }
                 else
                 {
                     Toast.makeText(requireContext(), "data not recive", Toast.LENGTH_SHORT).show()
                 }
             }

             override fun onCancelled(error: DatabaseError) {
                 Log.d("error", "error:${error} ")
             }

         })
    }

    companion object {

    }
}