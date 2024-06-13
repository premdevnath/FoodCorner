package com.example.lalit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lalit.databinding.BsheetitemBinding


class sadapter(var slist:ArrayList<smodal>,var requiredcontext: Context):RecyclerView.Adapter<sadapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sadapter.ViewHolder {
       var layoutInflater=LayoutInflater.from(parent.context)
        var binding=BsheetitemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: sadapter.ViewHolder, position: Int) {
        var item=slist.get(position)
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return slist.size
    }

    inner class ViewHolder(var binding:BsheetitemBinding):RecyclerView.ViewHolder(binding.root) {

        //[list par clik in searchfragment ]
        init {
            binding.root.setOnClickListener() {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    //itemClickListener?.onItemClick(position)


                }
                //[ek fragment se activity me intent aise pass akrte hai]
                //kuki yaha ek activity ko intent de rahe hai to context diya [ye scearfragment me bhi karna ahi]
                //ab is context ko iskke fragment me adapter me valye dallte hai vaha bhi dalo
                val intent = Intent(requiredcontext, details::class.java)
                intent.putExtra("itemname", slist[position].item)
                intent.putExtra("itemimage", slist[position].image)
                requiredcontext.startActivity(intent)


            }
        }

        fun bind(item:smodal)
        {
           binding.Bsimage.setImageResource(item.image)
            binding.Bsfoodname.text=item.item
            binding.Bsprice.text=item.price
         }
    }
    //r2.6 yaha slist me item ka name agay jise userne search kiya
    //fir us value ko slist me dalkar notify dtasetchanges kiya taki vo cihj user ko show ho 
    fun setFilteredList(slist: ArrayList<smodal>)
    {
        this.slist=slist
        notifyDataSetChanged()
    }
}