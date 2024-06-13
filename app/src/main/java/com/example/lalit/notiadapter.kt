package com.example.lalit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lalit.databinding.BfnitemBinding

class notiadapter(var notilist:ArrayList<notimodal>): RecyclerView.Adapter<notiadapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notiadapter.ViewHolder {
        var layoutInflater=LayoutInflater.from(parent.context)
        var binding=BfnitemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: notiadapter.ViewHolder, position: Int) {
        var item=notilist[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return notilist.size
    }
    class ViewHolder(var binding:BfnitemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: notimodal) {
           binding.noti.text=item.noti
            binding.nimage.setImageResource(item.iamge)
        }

    }
}