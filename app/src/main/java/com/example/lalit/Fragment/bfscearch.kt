package com.example.lalit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lalit.databinding.FragmentBfscearchBinding
import android.widget.SearchView
import com.example.lalit.Bsmodal
import com.example.lalit.bsadapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


//R15
//[yaha hamne data lene ke liye  bsmodal,bsadapter ka use kar liya kuki menubheet or bfsearch me same dat hai]


class bfscearch : Fragment() {

    lateinit var binding: FragmentBfscearchBinding
    lateinit var slist: ArrayList<Bsmodal>
    lateinit var adapter: bsadapter
    lateinit var database: FirebaseDatabase



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBfscearchBinding.inflate(inflater, container, false)
        slist = ArrayList<Bsmodal>()


        //R15[dataretrive] yaha search me bhi data base se data ko show kara rhe hai(or search or menubheet me same data dalna hai to bs ke adapter or modal class ko use kar rhe hai)
        retrivemenuitem()

        //R15.2setup search view( yaha serch view lagaya)
        setupsearchview()


        return binding.root


    }




    //R15.1(yahh pahle data ko data base se nikal ke slist me add kiya)
    private fun retrivemenuitem() {
        database=FirebaseDatabase.getInstance()
        val dataref:DatabaseReference=database.reference.child("menu")
        dataref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnap in snapshot.children)
                {
                    val menuitem=datasnap.getValue(Bsmodal::class.java)
                    menuitem?.let {
                        slist.add(it)
                    }
                }
                //R15.1.2(yah fun call kiya)
                showallmenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //15.1.3 yaha dapter call kiya
    private fun showallmenu() {

        val filterdata=ArrayList(slist)
        setadapter(filterdata)

    }


    //15.1.4 yaha data ko list seadpter me or adapter se rvi me dala
    private fun setadapter(filterdata: List<Bsmodal>) {
        adapter = bsadapter(filterdata,requireContext())
        binding.srvi.adapter = adapter
        binding.srvi.layoutManager = LinearLayoutManager(requireContext())
    }


    //R15.2 (yahserach view ko set kiya taki user kuch serch kare to vo uper aajey)
    private fun setupsearchview() {
        //1.search view me listner lagaya
        binding.search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            //2.yaha jab user search view me koi text dalega to filtermenuitem fun call hoga or vo text argu me jayega
            override fun onQueryTextSubmit(query: String?): Boolean {
                   filtermenuitems(query)
                return true
            }
            //3.yaha jab user search view me koi text me change  to filtermenuitem fun call hoga or vo text argu me jayega
            override fun onQueryTextChange(newText: String?): Boolean {
                filtermenuitems(newText)
                return true
            }

        })
    }

    //R15.3 yaha dekte hai ki jo text user ne search kiya hai us name ka food slist me hai
    private fun filtermenuitems(query: String?)
    {
        //1. pahle data ko slist se it me dala fir dekha ki query me jo text hai vo it me hai ya nhi (agar ye text hoga to ye true hoga)
        //or fir ye equal ho jayega
val filtrmenuitems=slist.filter{

    it.foodname?.contains(query!!, ignoreCase = true)  == true

             }
        //3. fir us data ko adapter me bheja
        setadapter(filtrmenuitems)
    }
    companion object {

    }
}




