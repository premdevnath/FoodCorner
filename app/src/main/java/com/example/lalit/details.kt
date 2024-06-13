package com.example.lalit

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lalit.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
                                     //most firbase rule
                                   //1. error aaye data show na ho to pahle path chek karo data add ka or data recive ka
                                   //2.data modal class me dekho varivle ke name or sequnce fire base ke me jo varible bana hai unke name or
                                  //or sequance se match kha rha hai ya nahi

class details : AppCompatActivity() {
    lateinit var binding:ActivityDetailsBinding

    //var cartquantity:Int?=null
    var dicryption:String?=null
    var foodimage:String?=null
    var foodname:String?=null
    var  price:String?=null
    var ingredient:String?=null


    //yaha auth hua kuki addcart sab user ka alag alag hoga(or baki pure app me sara data sabhi ke liye same hoga to isliye sabhi jagah auth nhi hua)
    lateinit var auth: FirebaseAuth
    lateinit var databaseref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dback.setOnClickListener(){
            finish()
        }


        //R1[database vala data get kiya](yaha data bfhome or menubottomsheet dono se aaraha hai)
        dicryption=intent.getStringExtra("fdic")
        Log.d("dis2", "opendetailsactivity: ${dicryption} ")
        foodimage=intent.getStringExtra("fimage")
        foodname=intent.getStringExtra("fname")
        ingredient=intent.getStringExtra("fing")
        price=intent.getStringExtra("fprice")



        //yaha ye getdata ko details activity ke image ,name (ui) me set kiya
       with(binding) {
            showname.text=foodname
            dicrep.text=dicryption.toString()
           ingre.text=ingredient.toString()
            val uriString=foodimage
            val uri= Uri.parse(uriString)
                         //ye ek acitivity he to isliye haya context ya require context nhi aaya
            Glide.with(this@details).load(uri).into(showimage)
        }

        auth=FirebaseAuth.getInstance()
        databaseref= FirebaseDatabase.getInstance().reference

        //[add to cart button ]
        //R2( jab koi user list ek kisi food per clik karga to uski details batane ke liye ye details activity open hogi
        //or vaha addtocart karne par keval us user ke liye vo food item add to cart karne ke liye pahle data ko firebase me add kiya)
         binding.daddcart.setOnClickListener(){

          addcart()
         }



        //ye demo data ko show kara rhe the uska code hia
        /*
        //inetent se pass data ko liya taki show kar ske
        var foodname=intent.getStringExtra("itemname")
        var foodimage=intent.getIntExtra("itemimage",0)
        binding.showname.text=foodname
        binding.showimage.setImageResource(foodimage)

         */

    }

    //[yaha s hum add to cart button par clik kar rhe hai]
    //R3 yaha phle chek kiya ki user pahle se hai ya nhi or hai to cartitem me data leke use menu ke anadar cartitem name ka path bana ke vaha save karaya
    private fun addcart() {
        val curretntuser=auth.currentUser?.uid?:""
        if(curretntuser!=null) {
            var cartitem = (cartrvmodal(1,
                dicryption.toString(),
                foodimage.toString(),
                foodname.toString(),
                price.toString(),
                ingredient.toString()

            ))                                       //n
            databaseref.child("user").child(curretntuser).child("cartitem").push().setValue(cartitem)
                .addOnSuccessListener {
                    Toast.makeText(this, "item add", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "item add faild", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

