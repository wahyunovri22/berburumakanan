package com.example.berburumakanan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.berburumakanan.MainActivity
import com.example.berburumakanan.R
import com.example.berburumakanan.databinding.RowStoreBinding
import com.example.berburumakanan.helper.Haversine
import com.example.berburumakanan.model.DataItem
import com.semicolon.newsapp.helper.Config
import com.squareup.picasso.Picasso
import java.util.Locale

class RestoAdapter(): RecyclerView.Adapter<RestoAdapter.Holder>(),Filterable {

    lateinit var context: Context
    lateinit var list: List<DataItem>
    lateinit var list2: List<DataItem>

    fun setData(context: Context,list: List<DataItem>){
        this.context = context
        this.list = list
        this.list2 = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = RowStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(private val binding:RowStoreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem){
            binding.tvNama.text = data.nama
            binding.tvOpen.text = data.open
//            val range = Haversine().hitungJarak(-7.1517404,110.4079024,data.lat!!.toDouble(),
//            data.longi!!.toDouble())
            //binding.tvRange.text = "$range"
            binding.tvRange.text = "--"

            Picasso.get().load(Config.PATH+data.cover)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgCover)
            binding.root.setOnClickListener {
                (context as MainActivity).gotoDetail(data)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val oReturn = FilterResults()
                val results2 = ArrayList<DataItem>()

                if (list2.size > 0) {
                    for (g in list2) {
                        if (g.nama.toString().lowercase(Locale.ROOT).contains(charSequence.toString())){
                            results2.add(g)
                        }
                    }
                }
                oReturn.values = results2
                return oReturn
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                list = filterResults.values as ArrayList<DataItem>
                notifyDataSetChanged()
            }
        }
    }
}