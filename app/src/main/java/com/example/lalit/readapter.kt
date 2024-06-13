package com.example.lalit

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lalit.databinding.RecenyitemBinding

class readapter(var rname:ArrayList<String>,var rprice:ArrayList<String>,var rfimage:ArrayList<String>,var rquantity:ArrayList<Int>,var context: Context):RecyclerView.Adapter<readapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): readapter.ViewHolder {
        var layoutInflater=LayoutInflater.from(context)
        val binding=RecenyitemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: readapter.ViewHolder, position: Int) {
      holder.bind(rname[position],
                   rprice[position],
                  rfimage[position],
                  rquantity[position])
    }

    override fun getItemCount(): Int {
       return rname.size
    }
    inner class ViewHolder(var binding:RecenyitemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(s: String, s1: String, s2: String, s3: Int) {
            binding.apply {
                rfoodname.text=s
                Log.d("s1", "bind:${s1} ")
                rprice.text=s1
                rfquantity.text=s3.toString()
                val image=s2
                val uri=Uri.parse(image.toString())
                Glide.with(context).load(uri).into(rimage)

            }
        }


    }
}