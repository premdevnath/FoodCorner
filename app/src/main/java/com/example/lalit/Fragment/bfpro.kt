package com.example.lalit.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lalit.R
import com.example.lalit.databinding.FragmentBfproBinding
import com.example.lalit.profilemodal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class bfpro : Fragment() {

    lateinit var binding:FragmentBfproBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBfproBinding.inflate(inflater,container, false)

        //data ko firebase se profile me show karane ke liye
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()


        //R[yaha  hamne profile ke edit text ko diabke kiya taki jab koi editbutton press kare tabhi vo profile edit kar sake]
        binding.apply {
            aname.isEnabled = false
            email.isEnabled = false
            aadress.isEnabled = false
            aphone.isEnabled = false
            //edit button
        binding.editbutton.setOnClickListener()
        {

                aname.isEnabled=!aname.isEnabled
                email.isEnabled=!email.isEnabled
                aadress.isEnabled=!aadress.isEnabled
                aphone.isEnabled=!aphone.isEnabled
            }
        }

        //R9***[data ko save kiya]
        //S1[data ko save karne ke liye save button(yani jai se profile me name ye to firebase se aajeyga lekin address ye jo cij user dalega profile me to use save karne ke liye)]
        binding.savep.setOnClickListener(){
            //s1. (yaha pahle jo data profile ke name,addrees inme dala us data ko firebse me add karke use profile me show karnge)
            var name=binding.aname.text.toString()
            var email=binding.email.text.toString()
            var address=binding.aadress.text.toString()
            var phone=binding.aphone.text.toString()
            //s2(yaha us data ko showdata fun me ko pass kiya)
            showdata(name,email,address,phone)
        }
        setuserdata()

        return binding.root
    }

    //s3 (yaha us data ko liya)
    private fun showdata(name:String,
                          email: String,
                         address:String,
                         phone:String)

    {
        //s4 yaha chek kiya kya vo auth user hai
       val currentuser=auth.currentUser?.uid
        if(currentuser!=null)
        {
            //s5 hai to pathme diya
            val showref=database.reference.child("user").child(currentuser)
                //s6 yaha hashmap ka varible banaya or usme sara data dala
            val showdata = hashMapOf(
                "name" to name,
               "email" to email,
               "address" to address,
               "phone" to phone
            )
            //s7 yaha us path me hashmap ke data ko dala
            showref.setValue(showdata)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "save data", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "faild", Toast.LENGTH_SHORT).show()
                }

        }

    }
    //R9.1***[ussi data ko show kiya]

    //[yaha jab user sing up karta hai to uska name or email ye sab firebase me save karte hai to is data ko user ki profile me show kara rhe hai]
    private fun setuserdata() {

        var currentuser=auth.currentUser?.uid
        if(currentuser!=null)
        {
            var data=database.reference.child("user").child(currentuser)
            data.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //chet
                    if(snapshot.exists())
                    {
                        var alldata=snapshot.getValue(profilemodal::class.java)
                        if(alldata!=null)
                        {
                            binding.aname.setText(alldata.name)
                            Log.d("data", "onDataChange:${alldata.name} ")
                            binding.aadress.setText(alldata.address)
                            Log.d("data", "onDataChange:${alldata.address} ")
                            binding.email.setText(alldata.email)
                            binding.aphone.setText(alldata.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("profile", "error:${error} ")
                }

            })
        }
    }

    companion object {

    }
}