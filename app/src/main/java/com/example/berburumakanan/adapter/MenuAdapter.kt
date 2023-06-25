package com.example.berburumakanan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.berburumakanan.R
import com.example.berburumakanan.databinding.RowMenuBinding
import com.example.berburumakanan.model.DataItem
import com.semicolon.newsapp.helper.Config
import com.squareup.picasso.Picasso

class MenuAdapter(var list: List<DataItem>): RecyclerView.Adapter<MenuAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = RowMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(private val binding:RowMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem){
            binding.tvTittle.text = data.nama
            binding.tvDes.text = data.deskripsi
            binding.tvPrice.text = data.harga
            Picasso.get().load(Config.PATH+data.cover)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgMenu)
        }
    }
}