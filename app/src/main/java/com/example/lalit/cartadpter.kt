package com.example.lalit

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lalit.databinding.CartitemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

                            //agar program chla rha ho lekin koi chi kam nhi kar rhi hai image nhi show ho rhi hai yah button nhi click ho rh ato use
                           //debug karne ke liye vaha ek log lagao (phone)


                     //rule m(ye vale name or cartitem ke view ke name same nhi nhi hone chaia)

                     //data leke show karne ke liye varible banana

                   //yaha firebase ka sequance follow karna
class cartadpter(
                       var cartquantity: ArrayList<Int>,
                       var dicryption: ArrayList<String>,
                       var foodname: ArrayList<String>,
                       var foodimage: ArrayList<String>,
                       var price: ArrayList<String>,
                       var ingredient: ArrayList<String>,
                       var context: Context
                   ):RecyclerView.Adapter<cartadpter.ViewHolder>()
{







   lateinit var cartquantitys:ArrayList<Int>

    var auth: FirebaseAuth
    var database: FirebaseDatabase

    var cartItemref:DatabaseReference
    public var itemquantity:IntArray= intArrayOf()





    //R3[pahle data base ka ek vrible ko ini. kiya]
    init {
        auth=FirebaseAuth.getInstance()
    database=FirebaseDatabase.getInstance()

        //1. pahle dekha ye auth user he fir cartitemnum. nikala(yani hamare pass kitne item(dish) hai)
        val cureentuser=auth.currentUser?.uid?:""
        val cartitemnumber=foodname.size

        //r1 [yaha hamne hamare pass jitne list hai uska size itemquantity me dala ]
        //2.(yaha  item(food dish) ki quantity set ki  )
        itemquantity=IntArray(cartitemnumber){1}
        //3.
        cartItemref=database.reference.child("user").child(cureentuser).child("cartitem")

    }
    //n


    //incres fun or decre fun me cartqunatity me chnags kiye (number kam,jyada) use bf cart ko vapas diye
    fun getupdateitemquantity(): MutableList<Int> {


       val itemquantitys = mutableListOf<Int>()
        itemquantitys.addAll(cartquantity)
        return itemquantitys
        Log.d("ndata", "getupdateitemquantity:${itemquantity} ")


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartadpter.ViewHolder {
      var layoutInflater=LayoutInflater.from(parent.context)
        var binding=CartitemBinding.inflate(layoutInflater)
        return ViewHolder(binding )
    }

    override fun onBindViewHolder(holder: cartadpter.ViewHolder, position: Int) {

        holder.bind(position)



    }

    override fun getItemCount(): Int {
      return foodname.size
    }





    //[cart me item plus r minus]

    //*[rule kuki ye ek inner class hai to yaha inner likhna nhi to kuch erraro(jaise bahar ki class ke varible ko ander use nhi kar sakte hai)]
   inner class ViewHolder(var binding:CartitemBinding):RecyclerView.ViewHolder(binding.root) {

        //[yaha onbindviewholder me aise hi karana ye code notes me mil jayega]
          fun bind(position: Int)
        {
              //R4 [yaha food name,price image name inko cartitem ke view me set kiya]

              //is function ko lagane se bar bar binding nhi likhan padta hai
              binding.apply {

                  //r1.1[yaha hamara pass kitni list hai unke number or uski position di]
                 // note.cartquantity

                  //w
                  val quantity =itemquantity[position]

                  cartfoodname.text = foodname[position]
                  Log.d("prem", "bind:$cartfoodname ")
                  cartprice.text =price[position]
                  Log.d("prem", "bind:$cartprice")
                  //r1.3[yaha yaha kintne list hai or unki kya position hai use cartitemquantity me add kara(jo hamne kitni chije cart me add kari vo batata hai)]
                  cartitemquantity.text=quantity.toString()
                  Log.d("premq", "bind:$cartitemquantity ")
                  //R[iamge ko data base se]
                  val uriString=foodimage[position]
                  Log.d("image", "fu:$uriString")
                  val uri= Uri.parse(uriString)
                  Glide.with(context).load(uri).into(cartfoodimage)


                         //[R4end]


                  //[yaha +,-,dletebuton par opretion kiya](yaha kiya kuki ye button cartitem.xml me hai jo yaha inflate ki hai)

                  //r1.4[yaha minus button press karte hai us card(list me se ek food item) ki postion decrefun me jayegi]
                  minus.setOnClickListener {
                      decreseQuantity(position)
                  }
                  plus.setOnClickListener {
                      increseQuantity(position)
                  }

                  //R5[yah user delete par clik karega to deleteite, fun call hoga]
                  //r4[yaha ek varible banaya jisme user jis list par clik karega uski positon is varible me aajeygi
                  // //1 fir if me condition chek hogi ki ye jo list ka ek item mila hai jiski position ye hai kya vo recyler view me hai or hai to dlete fun call hoag]
                  delete.setOnClickListener {
                      val itemposition=adapterPosition

                                    //yaha equal hona chahai tha lein not equla hai
                      if(itemposition!=RecyclerView.NO_POSITION){
                          deleteitem(itemposition)
                      }
                  }
              }
        }


        //ytvideo12[yaha chnges kiya]
        private  fun increseQuantity(position: Int){
            if(itemquantity[position]<10) //[r3 ayah hamne condition di hai 10 se jayada bar ek food item kpa dd nhi kar skte hai]
            {
                //f
                itemquantity[position]++
                cartquantity[position]=itemquantity[position]  //ye itemqunatity jese hi badi use cartquantity me dala
                Log.d("quantity", "increseQuantity: ${cartquantity} ")
                binding.cartitemquantity.text= itemquantity[position].toString() //ye itemqunatity jese hi badi use cartitemquantity me dala jis app me number bad jaye
                Log.d("quantity", "increseQuantity: ${binding.cartitemquantity} ")
            }
        }

        //[r1.4.1 (1)  minus butoon par clik karke yah aaye or us lsit ki position bhi laye fir
        //         (2) yaha if me cehk kara jo position vali (list) food laye hai uska number (usko user ne kitni bar add kiay) hai eg 4 hai to us posotion me itemqunatity ek kam hui
        //         (3) or us numbser ko us position ke lsit ke cartitemquantity(textview) me add kiya]
        private  fun decreseQuantity(position: Int){
            if(itemquantity[position]>1) //r3[yaha 1 se kam nhi kuki ek item to add hoga hi]
            {
                itemquantity[position]--
                //f
                cartquantity[position]=itemquantity[position]
                binding.cartitemquantity.text=itemquantity[position].toString()
            }
        }

        //r4.1 [1.ye fun call hua to pahle clist me us position me jo hoagause remove kara
        //      2. fir usee notify kara ki us position ko recyler view se remove karo
        //      3.fir ek or notify kara ki clist kis size ko change karo ]


        //R5.1[delte fun call]
        private  fun deleteitem(position: Int)
      {
            //1.jis bhi item(dish) par delete dabaya vaha ko position is varible me li
            val positionRetrive=position
                //2. fir yaha getUniqueKeyArposition function ko call kara(yaha ye is fun me chlo)
          getUniqueKeyArposition(positionRetrive) {uniqukey->
              //7. uniekey me data(list me food iamge,name quantity) ko retrun kiya(niche se) to ye null nhi hogi
              if(uniqukey!=null)
              {
                  //8. fir ye fun call hoga
                  removeItem(position,uniqukey)
              }

          }

      }


        //R 6[yaha getUniqueKeyArposition fun ko getUniqueKeyArposition fun se pata chle ke bat database me ki postion par vo list hai jispe user ne clik
        //kiya yaha use dlete karte hai]
        private fun removeItem(position: Int, uniqukey: String) {

            //1. yaha dekha uniqkey null to nhi hai(isme user ne jis list par clik kiya tha uska iamge,name ye hai)
            if (uniqukey!=null)
            {
                //2.null nhi hai to data base ye us lidt ke data ko delte kiya
                cartItemref.child(uniqukey).removeValue()
                    .addOnSuccessListener{
                        //3. fir us list ki position ko delete kiya
                        //extra
                        cartquantity.removeAt(position)
                        foodname.removeAt(position)
                        foodimage.removeAt(position)
                        dicryption.removeAt(position)
                        price.removeAt(position)
                        ingredient.removeAt(position)

                        Toast.makeText(context, "item remove", Toast.LENGTH_SHORT).show()

                    //upadate itemquantytyt
                        //4.itemquantity ko update kar rahe hain taaki jo item delete hua hai, uska quantity bhi remove ho jaye.
                        //(yaha indecx me us list ki position hai or i me us item ki quantity to use bhi remove kiay)
                        itemquantity = itemquantity.filterIndexed { index, i -> index != position }.toIntArray()
                        //5. yaha sabko bata diya ye lsit se ye item dlete hua hai or list ko vapas shai se set kardo
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,foodname.size)
                }.addOnFailureListener{
                        Toast.makeText(context, "faild to delete", Toast.LENGTH_SHORT).show()
                }
                    

            }
        }

        //

        //[R5.2](yaha chek kiya user ne jis list pe clik liya vo data base me hai ya nhi or vo list hogi to use  getUniqueKeyArposition() fun ko diya jayega]
        private fun getUniqueKeyArposition(positionRetrive: Int,onComplete:(String?)->Unit) {
cartItemref.addListenerForSingleValueEvent(object :ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        //3.yahaek uniqkey varible banaya
       var uniqukey:String?=null
        //4.fir yaha snapshot ye sara data datasnapshot mr liya or sabhi list ke positio index me li(kuki yaha list data ke sath uski position bhi chaia thi islye foreach index loo[ lagaya)
        snapshot.children.forEachIndexed{index, dataSnapshot ->
            //5.yaha dekha jis list par user ne clik kiya uski jo position mili vo idex(database ki list ) me he ya nhi
            //or hogi to ye true hoga(or vo list database me nhi hui to to ye true nhi hoga or uniiqkey khali hogi)
            if(index==positionRetrive)
            {
                //6.user ne jis list pe clik kiya vo database me hai to snapshot se sara data us position par jo list hai uska uniqkey me dala
                //or use   getUniqueKeyArposition fun ke retyrn kiya
                uniqukey=dataSnapshot.key
                return@forEachIndexed
            }
        }
        onComplete(uniqukey)
    }

    override fun onCancelled(error: DatabaseError) {

    }

})
        }

}



}


