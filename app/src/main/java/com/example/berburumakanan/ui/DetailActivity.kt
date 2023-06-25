package com.example.berburumakanan.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.berburumakanan.R
import com.example.berburumakanan.adapter.MenuAdapter
import com.example.berburumakanan.databinding.ActivityDetailBinding
import com.example.berburumakanan.model.DataItem
import com.example.berburumakanan.model.ResponseResto
import com.example.berburumakanan.network.ApiConfig
import com.semicolon.newsapp.helper.Config
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object{
        const val DATA = "data"
    }

    lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        main()
    }

    private fun main(){
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DATA,DataItem::class.java)
        } else {
            intent.getParcelableExtra(DATA)
        }

        binding.tvNama.text = uri?.nama
        binding.tvPhone.text = uri?.phone
        binding.tvOpen.text = uri?.open
        binding.tvWeb.text = uri?.web

        Picasso.get().load(Config.PATH+uri?.cover)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imgCover)

        getData(uri?.id?:"")

        binding.btnMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("google.navigation:q="+uri?.lat+","+uri?.longi + "&mode=d")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun getData(id:String){
        val pdDialog = ProgressDialog.show(this,"Loading","Please Wait ....",true)
        ApiConfig.getInstanceRetrofit().menu(id).enqueue(object : Callback<ResponseResto> {
            override fun onResponse(call: Call<ResponseResto>, response: Response<ResponseResto>) {
                pdDialog.dismiss()
                if (response.isSuccessful){
                    if (response.body()?.data?.size!! > 0){
                        setData(response.body()?.data)
                    }else{
                        Toast.makeText(this@DetailActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseResto>, t: Throwable) {
                pdDialog.dismiss()
                Toast.makeText(this@DetailActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(list: List<DataItem>?){
        list?.let {
            val menuAdapter = MenuAdapter(it)
            with(binding.rvMenu){
                this.layoutManager = LinearLayoutManager(this@DetailActivity)
                this.adapter = menuAdapter
            }
        }
    }
}