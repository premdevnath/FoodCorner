package com.example.lalit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lalit.databinding.ActivityPaymentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class payment : AppCompatActivity() {
    lateinit var binding:ActivityPaymentBinding
    lateinit var aname:String
    lateinit var aadress:String
    lateinit var phone:String
    lateinit var amount:String
    lateinit var   fname :ArrayList<String>
    lateinit var fprice  :ArrayList<String>
    lateinit var fimage   :ArrayList<String>
    lateinit var fdic    :ArrayList<String>
    lateinit var fing     :ArrayList<String>
    lateinit var fquantity :ArrayList<Int>
    lateinit var userid:String
    lateinit var auth: FirebaseAuth
    lateinit var databaseref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentback.setOnClickListener()
        {
            finish()
        }

        //R13[start]
        //13.1(payment activity me butoon ke clik pahele chek kiya sara data fill kiya user ne)
        binding.payout.setOnClickListener()
        {
            //yaha payment button ke clik par is data ko get kar rhe hai
            aname=binding.aname.text.toString().trim()
            aadress=binding.aadress.text.toString().trim()
            phone=binding.aphone.text.toString().trim()
            if(aname.isBlank()&&aadress.isBlank()&&phone.isBlank())
            {
                Toast.makeText(this, "please fill data", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //13.2 kiya to ye fun call hua
                pleaseorder()
            }

            //button par clik karne par bscongrat fragment ope hua

        }

        //get user data from firebase
        val intent = intent
        fname=intent.getStringArrayListExtra("fname") as ArrayList<String>
        fprice=intent.getStringArrayListExtra("fprice") as ArrayList<String>
        fdic=intent.getStringArrayListExtra("fdic") as ArrayList<String>
        fimage=intent.getStringArrayListExtra("fimage") as ArrayList<String>
        fing=intent.getStringArrayListExtra("fing") as ArrayList<String>
        fquantity=intent.getIntegerArrayListExtra("fquantity") as ArrayList<Int>

        //fun call jaha se total amount milega
        amount = calcuateamount().toString() +"$"
        binding.amount.setText(amount)


        auth=FirebaseAuth.getInstance()
        databaseref=FirebaseDatabase.getInstance().reference
        getuserdata()

    }

    //[order history yaha bana ]
//R13.2.0(yaha pahle dekha user auth he fir data base me new path create kiya or vaha push key lagai taki har bar us path par alag alag jagah dta store hi)
    private fun pleaseorder() {
     userid=auth.currentUser?.uid?:""
    //13.2.1yaha timing lagai jo time batayga
        val time=System.currentTimeMillis()
        val itempushkey=databaseref.child("orderdetails").push().key
    //13.2.2(yaha modalclass ko data diya)[yaha moal class ko parceble banaya]
        val orderdetails=orderdetails(aname,aadress,amount,phone,fname,fprice,fimage,fquantity,userid,time,itempushkey,false,false)
    //13.2.3 (fir jo path banaya vaha data setvalue ki orderdetails ki)
                                                             //eg.
                                                                 //1.data kaha save karana hai us path me gai(orderdetails)
                                                                 //2.us path ke andar har bar ek alag name ganrate hoga kuki path ke andar itempush key hai(user1)
                                                                 //3. fir value ko us jagah dala jayega
        val orderRef=databaseref.child("orderdetails").child(itempushkey!!)

        orderRef.setValue(orderdetails).addOnSuccessListener {
           //agar ye hogay to congrat vali bottomsheet khul jayegi
            val bottomSheetDialog=bscongrats()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            //13.2.4 fun call
            removeitemcart()
            //13.2.5 fun call
            addorderhistory(orderdetails)


        }
            .addOnFailureListener {
                Toast.makeText(this, "faild to order", Toast.LENGTH_SHORT).show()
            }
    }

    //R13.4 [jaise hi user ne food order kar diya to us item ko cart ye remove karna hoga or us order ko histry vale fragment me show karna hoga]
    //(yaha data remove karne ke bad cart fragmnes se use history f. me show karne ke liye)
    //1. phale ek path diya (user) fir userid(yani current user ki jo id banti hai) di fir 3 name(byuhistory)
    //2. uske andar itempushkey dali taki har bar data alag alg name me save ho byuhistory me
    //3.fir vaha orderdetals ka sara data dal diya
    private fun addorderhistory(orderdetails: orderdetails){

        databaseref.child("user").child(userid).child("ByuHistory")
            .child(orderdetails.itempushkey!!)
            .setValue(orderdetails).addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(this, "faild to order", Toast.LENGTH_SHORT).show()
            }
    }

    //R13.3 [jaise hi user ne food order kar diya to us item ko cart ye remove karna hoga or us order ko histry vale fragment me show karna hoga]
    //(to yaha hamne bfcart me cart ka data jo user ke andar cartitem me path me save kiay tha user remove kiya)
    private fun removeitemcart() {
        val cartItemref=databaseref.child("user").child(userid).child("cartitems")
        cartItemref.removeValue()

    }



    private fun calcuateamount(): Int {
         var total=0
        for(i in 0 until fprice.size)
        {
            var price=fprice[i]
            var lastchar =price.last()
            var priceIntvalue= if ( lastchar =='$'){
                price.dropLast(1).toInt()

            }
            else
            {
                price.toInt()
            }

            var quantity=fquantity[i]
            Log.d("cart", "calcuateamount:${quantity} ")
            total += priceIntvalue * quantity
        }

        return total
    }

    //[p4 yaha pahle cehk kiya ki user auth he ya nhi]

    private fun getuserdata() {
        val currentuser=auth.currentUser
        if(currentuser!=null)
        {
            //chet
            var userid=currentuser.uid

            //1. yaha database se un data ko get kiya
            val dataref=databaseref.child("user").child(userid)
            dataref.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshoot in snapshot.children)
                   {

                                                          //bfcart
                       var address =snapshoot.child("address").getValue(String::class.java)?:""
                       var name=snapshoot.child("name").getValue(String::class.java)?:""
                       Log.d("newdata", "onDataChange:${name} ")
                       var phone =snapshoot.child("phone").getValue(String::class.java)?:""

                       //2. fir us data ko ye payment actvity ke view (name,adress) inko de diya
                       binding.apply {
                           aname.setText(name)
                           aadress.setText(address)
                           aphone.setText(phone)
                       }
                   }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@payment, "faild", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}