package com.example.lalit.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.lalit.Menubheet
import com.example.lalit.R
import com.example.lalit.adapter
import com.example.lalit.databinding.FragmentBfhomeBinding
import com.example.lalit.rvmodal
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class bfhome : Fragment()
{
    private lateinit var binding:FragmentBfhomeBinding
    lateinit var adapter: adapter
    lateinit var listdata:ArrayList<rvmodal>
    lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentBfhomeBinding.inflate(inflater, container, false)


        //*1.1 //yaha viewmenu par click karne par menubseet ko call kiya or use bottomSheetDialog varible me dala
        // fir is bottomshett fragment me show lagaya taki ye sho ho jaye

        binding.allmenu.setOnClickListener {
            val bottomSheetDialog = Menubheet()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        //initillize
        database=FirebaseDatabase.getInstance()
        dataretrive()
        return binding.root

    }


//yaha hum data base se data ko retrive kar rhe hai
    private fun dataretrive() {
    listdata= arrayListOf()
       var dataref=database.reference.child("menu")
        dataref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                listdata.clear()
                for (foodsnapshot in snapshot.children)
                {
                    var menu=foodsnapshot.getValue(rvmodal::class.java)
                    menu.let {
                        listdata.add(it!!)
                    }
                }
               // Log.d("error:", "onDataChange: data recive")
                //adpter ko call kiya
                adapterdata()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //list se data ko adpter me dala
    private fun adapterdata() {
        //r1.1 yaha ye data diya
        //r3[2*[listadat me data add]]

        if(listdata.isNotEmpty()) {
            adapter = adapter(listdata, requireContext())
            binding.rvi.adapter = adapter
           // Log.d("error:", "onDataChange: data set")
            binding.rvi.layoutManager =
                LinearLayoutManager(requireContext()) //***most e1[ye sab onCreateView() fun me nhi ho rha hai or ye fragment hai to yaha adapter ko context(this) nhi de sakte hai
            //to kabhi this me erroe(type mismatch: inferred type is bfhome but context was expected) aaye  to requircontext ka use karna
            //ya is error kogoogle pe dal ke stackoverflow se answer le lena
        }
        else
        {
            Log.d("error:", "onDataChange: data not set")
        }

    }

    

    //r1 yaha demodata (yani image,price,itemname ko is fun me diya kuki ye fragment hai activity hoti to oncretview me dete)

    //*1[image silder (iski dependnacy github se downlod kari ,or maven url bhi)]

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //r2[imagesilder ka sara code git hub se liya hai(  imagesilder android github)]
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object : ItemClickListener {

            //userbanner par clik kare
            override fun onItemSelected(position: Int) {
                var images = imageList[position]
                var massage =
                    "slecete image $position" //hum jis bhi image ko salect karenge uski position massege me aajagi or use toats me print kara rah hai
                Toast.makeText(requireContext(), massage, Toast.LENGTH_SHORT).show()
            }

            override fun doubleClick(position: Int) {

            }
        })


    }
    companion object {

    }
}