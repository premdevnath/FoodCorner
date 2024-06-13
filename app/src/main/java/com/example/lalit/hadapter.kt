package com.example.lalit

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lalit.databinding.HitemBinding
import java.net.URL

class hadapter( val bfname:MutableList<String>,
                val bfprice: MutableList<String>,
                val bfimage: MutableList<String>,
                  val requirecontext:Context):RecyclerView.Adapter<hadapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): hadapter.ViewHolder {
       var inflater=LayoutInflater.from(parent.context)
        var binding=HitemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: hadapter.ViewHolder, position: Int) {
        //R14.4.1 yaha us data or uski position direct bind fun me pass ki
        holder.bind(
        bfname[position],
        bfprice[position],
        bfimage[position]
        )

    }

    override fun getItemCount(): Int {
       return bfimage.size
    }
   inner class ViewHolder(var binding:HitemBinding):RecyclerView.ViewHolder(binding.root) {

       //R14.4.2(yaha vo value or vo position is fun ne li or use hitem ke image,price inme et kiya)
        fun bind(bfname: String,bfprice:String,bfimage:String) {

            binding.hfoodname.text=bfname
            binding.hfprice.text=bfprice
            val image = bfimage
            val uri=Uri.parse(image)
            Glide.with( requirecontext).load(uri).into(binding.hfimage)
        }

    }
}