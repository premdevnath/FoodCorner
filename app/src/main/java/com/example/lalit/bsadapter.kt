package com.example.lalit

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lalit.databinding.BsheetitemBinding



                                   //ye bf search or bhottom shett dono item.xml same hai

class bsadapter(var listdata: List<Bsmodal>, var requiredcontext:Context) : RecyclerView.Adapter<bsadapter.ViewHolder>()
{



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bsadapter.ViewHolder {
        //*** e2[adapter me context var na ho to haya context parent ko laga ke likhete hai]
        var layoutInflater = LayoutInflater.from(parent.context)
        var binding = BsheetitemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: bsadapter.ViewHolder, position: Int) {
        //dusra tarika onbindview(view ko main screen me show karne ka)
        var item = listdata[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listdata.size//yaha jitne item hai kahne ke unki size ko retrun kiya
    }

    inner class ViewHolder(var binding: BsheetitemBinding) : RecyclerView.ViewHolder(binding.root) {

        //menu me list ke kisi item ar click karne par us item ka name,image ko details vali activity me bhej rhe rhai
        init {
            binding.root.setOnClickListener() {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {


                    //r1[data retrive in lalitp]
                    opendetailsactivity(position)
                }
                //[ek fragment se activity me intent aise pass akrte hai]
                //kuki yaha ek activity ko intent de rahe hai to context diya [ye scearfragment me bhi karna ahi]
                //ab is context ko iskke fragment me adapter me valye dallte hai vaha bhi dalo
              /*  val intent = Intent(requiredcontext, details::class.java)
                intent.putExtra("itemname", listdata[position].item)
                intent.putExtra("itemimage", listdata[position].image)
                requiredcontext.startActivity(intent)

               */


            }
        }

        //r3 yaha se value ko details activty  me show karane ke liye put kar rhe hai
        private fun opendetailsactivity(position: Int) {
            var item=listdata[position]
            var intent =Intent(requiredcontext,details::class.java)
            intent.putExtra("fdic",item.dicryption)
            Log.d("dis", "opendetailsactivity: ${item.dicryption} ")
            intent.putExtra("fimage",item.foodimage)
            intent.putExtra("fname",item.foodname)
            Log.d("dis", "opendetailsactivity: ${item.foodname} ")
            intent.putExtra("fing",item.ingredient)
            Log.d("dis", "opendetailsactivity: ${item.ingredient} ")
            intent.putExtra("fprice",item.price)


            requiredcontext.startActivity(intent)
        }

        //r3.1[yaha ye bsfragment me data show hoga]
        fun bind(item: Bsmodal) {

            val uriString=item.foodimage
            val uri= Uri.parse(uriString)
            Glide.with(requiredcontext).load(uri).into(binding.Bsimage)
            binding.Bsprice.text = item.price
            binding.Bsfoodname.text = item.foodname
        }

    }

}





