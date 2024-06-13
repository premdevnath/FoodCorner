package com.example.lalit.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lalit.R
import com.example.lalit.adapter
import com.example.lalit.databinding.FragmentBfhistoryBinding
import com.example.lalit.hadapter
import com.example.lalit.hmodal
import com.example.lalit.orderdetails
import com.example.lalit.recentbuy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class bfhistory : Fragment() {
    lateinit var binding:FragmentBfhistoryBinding
    lateinit var hlist:ArrayList<hmodal>
    lateinit var adapter: hadapter
    lateinit var auth:FirebaseAuth
    lateinit var userId:String
    lateinit var database:FirebaseDatabase

    private  var listoforderitem:ArrayList<orderdetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBfhistoryBinding.inflate(inflater, container, false)


        //ini
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        //R14[data show in history]
        //note[1. pahle recentbyu par vo dekhenge jo user ne add to cart kiya hoaga sabse last me]
                 //or agar ek se jayad acijhe hogi to us item par clik karne par jini bhi cijhe usne last me add ki hai vo sab show hogi
            //[2. yaha fir privius byu me jo item add hoke time ho gaya gaia unko rvi me show kara rhe hai]
        //yaha hum paymet ke button press par jo 13.4 me jo history save karai use yaha show karenge
        showbyuhistory()

        //recent buy clik(view kw clik par intent nhi pass kar skte hai to ek fun call karnge)
        binding.recentbi.setOnClickListener(){
         seeitemrecent()
        }


        //R17new.1 (is button par clik karne par latip me pendingorder activty me not recive (red colour)se recive(green) ho jayega)
        binding.reciveb.setOnClickListener(){
            //call fun
            updateorderstatus()
        }

        return binding.root
    }

//17new .2()
    private fun updateorderstatus() {
        val itempushkey=listoforderitem[0].itempushkey
        val compleorderref=database.reference.child("Comapledorder").child(itempushkey!!)
                                     //change ye coplet order jaha fire me juda vada payment reciv or orderaccepted  ko bhi add
       compleorderref.child("paymentrecive").setValue(true)
    }

    //R14.5[is data ko recentbuy activity me bhej rhe hai]
    private fun seeitemrecent() {
       listoforderitem.firstOrNull().let { data->
           val intent=Intent(requireContext(),recentbuy::class.java)
           intent.putExtra("rebuy",listoforderitem)
           startActivity(intent)


       }
    }


    //R14.1(pahle history dikahne vale ko bydefault hide rkehnge kuki user ne kuch order nhi kiya hoga to kya hsitory show hogi)
    private fun showbyuhistory() {
        //note (yaha hub constratlayout ko invisible kar rhe hai lekin hame to cartview ko karna tha)
      binding.recentbi.visibility=View.INVISIBLE
        //1.(yaha chek kiya user auth he(yani cureentuser ki uid hai))
        userId= auth.currentUser?.uid ?:""

        //2.(yaha path diya data kaha se lena hai)(is path me data payment R14.3 me dala tha)
        val dataref:DatabaseReference=database.reference.child("user").child(userId).child("ByuHistory")

       //2.1(yaha vaha se data sorting karke nikalenge)                                                //(is name ko firebase pe chek kar lena)
        val sortquery=dataref.orderByChild("currenttime")
        sortquery.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buysnapshoot in snapshot.children)
                {
                    val buyhistoryitem=buysnapshoot.getValue(orderdetails::class.java)
                    buyhistoryitem.let {
                        //3.(data ko is varible me add kia)
                        listoforderitem.add(it!!)
                        Log.d("data", "onDataChange: recive")
                    }
                }
                //3.1 or revers kiya taki jo lat me add ho vo pahle dikhe
                listoforderitem.reverse()
                //4.(data aagay to fun call hua)
                if(listoforderitem.isNotEmpty())
                {
                    setdatabuyrecenitem()//14.2
                    setpriviousitemrvi()//14.3
                    setdatainrecentviewitem()//14.4

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    //R14.2(yaha recet byu ko set kiya)
    private fun setdatabuyrecenitem() {
        //1.(data ane par(yani user ne kuch cart me dala) to ye fun call hua to isliye recentbi ko visible kiya)
       binding.recentbi.visibility=View.VISIBLE
        //2.(data ko pahle listoforderitem se nakal ke recentorder var me fir isse is data ko recent buy ke ui [bfhistory.xml](imag,name,price) set kiya
        val recentorder=listoforderitem.firstOrNull()
        recentorder?.let {
            binding.apply {
                                          //yaha fname ki jagah(aname ho skta hai agar ue sahi show na ho to ye karke dekhna)
                hrfoodname.text= it.fname?.firstOrNull()?:""
                Log.d("ldata", "setdatabuyrecenitem:${it.fname} ")
                hrprice.text=it.fprice?.firstOrNull()?:""
                Log.d("ldata", "setdatabuyrecenitem:${it.fprice} ")
                //image show ke liye gilde
                //p
                Log.d("ldata", "setdatabuyrecenitem:${it.fimage} ")
                val image=it.fimage?.firstOrNull()?:""
                Log.d("ldata", "setdatabuyrecenitem:${image} ")
                val uri= Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(hrimage)




                //17()
                val isorderisaceept=listoforderitem[0].orderacept
                Log.d( "recive:", "bind:${isorderisaceept} ")

                 //truelagana//c
                if(isorderisaceept == true)
                {
                    orderstatus.background.setTint(Color.GREEN)
                    reciveb.visibility=View.VISIBLE

                }

                listoforderitem.reverse()
                if(listoforderitem.isNotEmpty())
                {

                }

            }
        }
    }

    //14.3[ yaha fir privius byu me jo item add hoke time ho gaya gaia unko rvi me show kara rhe hai]
          //yaha hum paymet ke button press par jo 13.4 me jo history save karai use yaha show karenge
    private fun setpriviousitemrvi() {
        val bfname= mutableListOf<String>()
        val bfprice= mutableListOf<String>()
        val bfimage= mutableListOf<String>()

        //1.(yaha ika man jab tak llistoforderitem ki size(jitnr item honge utni size hogi) se chota hoga ye loop chlega)
        for(i in 1 until listoforderitem.size)
        {
            //2(pahle i ka man 1 to 1 list ki price,image,name ko recylcer view me add karenge aise ye chlta rahega)
            listoforderitem[i].fname?.firstOrNull()?.let {
                bfname.add(it)
            }
            listoforderitem[i].fprice?.firstOrNull()?.let {
                bfprice.add(it)
            }
            listoforderitem[i].fimage?.firstOrNull()?.let {
                bfimage.add(it)
            }

            //R14.4[fir use adapter me set kiya (yaha dat ko ek list data me dal kar set nhi kiya bakli alag alag set kiya)]
            //adpetr me jao
            adapter= hadapter(bfname,bfprice,bfimage,requireContext())
            binding.hrvi.adapter=adapter
            binding.hrvi.layoutManager=LinearLayoutManager(requireContext())
        }
    }

    private fun setdatainrecentviewitem() {


    }

    companion object {

    }
}


