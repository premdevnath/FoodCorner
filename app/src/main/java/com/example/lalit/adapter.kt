package com.example.lalit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.toolbar.MenuItem.OnClickListener
import com.bumptech.glide.Glide
import com.example.lalit.databinding.RvitemBinding


//yaha hamne rvmodal claas nhi abani isliye aise alag alag chije aarahi hai
class adapter(var listdata:ArrayList<rvmodal>,var requireContext: Context) :RecyclerView.Adapter<adapter.ViewHolder>(){
   // private var itemClickListener: bsadapter.OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter.ViewHolder {
                                                       //*** e2[adapter me context var na ho to haya context parent ko laga ke likhete hai]
      var layoutInflater=LayoutInflater.from(parent.context)
        var binding=RvitemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: adapter.ViewHolder, position: Int) {
        //dusra tarika onbindview(view ko main screen me show karne ka)
     holder.bind(position)
    }

    override fun getItemCount(): Int {
      return listdata.size//yaha jitne item hai kahne ke unki size ko retrun kiya
    }
  inner  class ViewHolder (var binding:RvitemBinding):RecyclerView.ViewHolder(binding.root){

      init {
          binding.root.setOnClickListener() {
              val position = adapterPosition
              if (position != RecyclerView.NO_POSITION) {
                 // itemClickListener?.onItemClick(position)
                  pmenudata(position)


              }
              //[ek fragment se activity me intent aise pass akrte hai]
              //kuki yaha ek activity ko intent de rahe hai to context diya [ye scearfragment me bhi karna ahi]
              //ab is context ko iskke fragment me adapter me valye dallte hai vaha bhi dalo
           /*   val intent = Intent(requireContext, details::class.java)
              intent.putExtra("itemname", listdata[position].item)
              intent.putExtra("itemimage", listdata[position].image)
              requireContext.startActivity(intent)

            */

          }
      }

      //detail activity ko data diya  //p
      private fun pmenudata(position: Int) {
          val item=listdata[position]
          var intent=Intent(requireContext,details::class.java)
          intent.putExtra("fdic",item.dicryption)
          intent.putExtra("fimage",item.foodimage)
          intent.putExtra("fname",item.foodname)
          intent.putExtra("fing",item.ingredient)
          intent.putExtra("fprice",item.price)

          requireContext.startActivity(intent)

      }


      //data ko is framgment me show karaya
      fun bind(position: Int)
        {
          val menuitem=listdata[position]
            val uriString=menuitem.foodimage
            Log.d("image", "uri:$uriString")
            val uri= Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.foodview)
            binding.price.text=menuitem.price
            binding.foodname.text=menuitem.foodname
        }
    }
}