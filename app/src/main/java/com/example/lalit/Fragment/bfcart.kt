package com.example.lalit.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lalit.cartadpter
import com.example.lalit.cartrvmodal
import com.example.lalit.databinding.FragmentBfcartBinding
import com.example.lalit.payment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//[activity ya fragment me data ko database ke path se nikal kar use recyler view me add karte hai]


class bfcart : Fragment()
{
    lateinit var cartquantity: ArrayList<Int>
    lateinit var dicryption: ArrayList<String>
    lateinit var foodimage: ArrayList<String>
    lateinit var foodname: ArrayList<String>
    lateinit var price: ArrayList<String>
   lateinit var ingredient: ArrayList<String>
    lateinit var binding: FragmentBfcartBinding
    lateinit var adapter: cartadpter
    lateinit var userid: String
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
        binding = FragmentBfcartBinding.inflate(inflater, container, false)

        //R1[database]
        //1.(yaha pahle database ke varible initillze kiya)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //2.(yaha retrivecartittem fun ko call kiya)



        //***[1]pahle ye fun call hua jise data ko firebase se nikal ke adapter ko diya
        retrivecartitem()


        //***[2] ye second num pe chalta hai[jisme kisi ne qunatity kam jyada ya delte ki hai ye batata hai]
        //(yani cartadapter me data update(quanvtity kam jyada) karen ke bad use data base me save karte hai yaha us data ko rretrive kiya jata hai or jab payment button par clik hota hai to ye data ko payment activityt ko diya jata hai)
        //P1[payment]
        //button jise press karne par getorder fun call hoga
        binding.proceed.setOnClickListener() {
            //ye fragmnet hai to yaha this ki jagah ye aata hai

            getorder()


        }



        return binding.root
    }

    //youyube
    //[p2 1.pahle in sabhi fname,fprice inme data base se data ko add kiya]
    private fun getorder() {


        var orderref: DatabaseReference =
            database.reference.child("user").child(userid).child("cartitem")
        cartquantity = ArrayList<Int>()
        dicryption = ArrayList<String>()
        foodname = ArrayList<String>()
        foodimage = ArrayList<String>()
        price = ArrayList<String>()
        ingredient = ArrayList<String>()





        //cartadpter me jo fun  cartquantity me jo data kam ko return kaar rha tha use ab fquantity me dala
        val fquantity = adapter.getupdateitemquantity()
        orderref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshoot in snapshot.children) {
                    var orderitem = snapshoot.getValue(cartrvmodal::class.java)

                        //yaha quantity nhi hai kuki quantity ka data to fquantity me aaya hai
                    orderitem?.dicryption.let {
                        dicryption.add(it!!)
                    }
                    orderitem?.foodimage.let {
                        foodimage.add(it!!)
                    }
                    orderitem?.foodname.let {
                        foodname.add(it!!)
                    }
                    orderitem?.ingredient.let {
                        ingredient.add(it!!)
                    }
                    orderitem?.price.let {
                        price.add(it!!)
                    }


                    /* orderitem.let {
   cartquantity.add(it.toString())
}

 */
                    //2. (yaha inme data add karne ke bad ordernow fun call kiya or unhe ye data pass kiya)
                    ordernow(foodname, foodimage, price, dicryption, ingredient, fquantity)

                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "order making failed", Toast.LENGTH_SHORT).show()
            }

        })
    }

    //[p3 yaha un argu ko liya or unhe database me put kiya]
    private fun ordernow(
        fname: ArrayList<String>,
        fimage: ArrayList<String>,
        fprice: ArrayList<String>,
        fdic: ArrayList<String>,
        fing: ArrayList<String>,
        cartquantity: MutableList<Int>
    ) {

        //*** upar
        if (isAdded && context != null) {
            var intent = Intent(requireContext(), payment::class.java)
            intent.putExtra("fname", fname as ArrayList<String>)
            intent.putExtra("fprice", fprice as ArrayList<String>)
            intent.putExtra("fdic", fdic as ArrayList<String>)
            intent.putExtra("fing", fing as ArrayList<String>)
            intent.putExtra("fimage", fimage as ArrayList<String>)
            intent.putExtra("fquantity", cartquantity as ArrayList<Int>)
            startActivity(intent)


        }
    }


    //[cartitem ko yaya retrive kar rhe hai jise details activity me add kar rhe hai]

    //R2[]
    private fun retrivecartitem() {
//1.(yaha pahle chek kiya ye currenyuser authentic he ya nhi)
        userid = auth.currentUser?.uid ?: ""
        //2.  or agar user sahi hai to data jaha hai uska path diya
// agar data show na ho to dekhna path sahi hai kya
        val foodref: DatabaseReference =
            database.reference.child("user").child(userid).child("cartitem")


        cartquantity = arrayListOf()
        dicryption = arrayListOf()
        foodimage = arrayListOf()
        foodname = arrayListOf()
        price = arrayListOf()
        ingredient = arrayListOf()



//3.(fir data ko datbase se cartmodal me chnage karke cartitem me dala fir us data ko clist me add kiya)
        foodref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshoot in snapshot.children) {
                    var cartitem = snapshoot.getValue(cartrvmodal::class.java)

                    //
                    cartitem?.foodname.let { foodname.add(it!!) }
                    cartitem?.price.let { price.add(it!!) }
                    cartitem?.dicryption.let { dicryption.add(it!!) }
                    cartitem?.foodimage.let { foodimage.add(it!!) }
                    cartitem?.cartquantity.let { cartquantity.add(it!!) }
                    //error
                   cartitem?.ingredient.let { ingredient.add(it!!) }

                 //   Toast.makeText(context, "data add successfull", Toast.LENGTH_SHORT).show()
                }

                Log.d("error1", "onDataChange: ${cartquantity}")
//4.yaha adapter ko calll kiya
                setadapter()

            }
            //5. yaha clist ne data ko adapter ko diya fir us data ko rvi me dala
            private fun setadapter() {

                adapter = cartadpter(
                    cartquantity,
                    dicryption,
                    foodname,
                    foodimage,
                    price,
                    ingredient,
                    requireContext()
                )
                binding.rvi2.adapter = adapter
                Log.d("error:", "onDataChange: data set")
                binding.rvi2.layoutManager =
                    LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
                //

            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }

        })

        /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
super.onViewCreated(view, savedInstanceState)


}

 */


    }
    companion object
    {

    }


}




