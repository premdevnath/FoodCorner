package com.example.lalit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lalit.Fragment.bfhistory
import com.example.lalit.databinding.ActivityRecentbuyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class recentbuy : AppCompatActivity() {
    lateinit var binding:ActivityRecentbuyBinding
    lateinit var rname:ArrayList<String>
    lateinit var rprice:ArrayList<String>
    lateinit var rimage:ArrayList<String>
    lateinit var rquantity:ArrayList<Int>
    lateinit var adapter: readapter
    lateinit var auth:FirebaseAuth
    lateinit var database:FirebaseDatabase
    lateinit var databaseref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRecentbuyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //
        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()


        //
        binding.rback.setOnClickListener(){

            finish()
        }
        //R14.5.1

        //[yaha bfhistrory me se putdata ko yaha get karke recenyitem.xml ke name,price yaha laga diya]
        val recentorderitem= intent.getSerializableExtra("rebuy") as ArrayList<orderdetails>
        recentorderitem.let { orderdetail->
            if(orderdetail.isNotEmpty()) {
                val recentoi=orderdetail[0]
                Log.d("my", "onCreate:${recentoi.fname} ")

                rname = recentoi.fname as ArrayList<String>
                rprice = recentoi.fprice as ArrayList<String>
                rimage = recentoi.fimage as ArrayList<String>
                rquantity = recentoi.fquantity as ArrayList<Int>


            }
        }
       // setdata()
        adapter= readapter(rname,rprice,rimage,rquantity,this)
        binding.recentrvi.adapter=adapter
        binding.recentrvi.layoutManager=LinearLayoutManager(this)

    }

    /*
    private fun setdata() {
     val currentuser=auth.currentUser?.uid?:""

       databaseref= database.reference.child("").child(currentuser)

    }

     */
}