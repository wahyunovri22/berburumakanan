package com.example.berburumakanan

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.berburumakanan.adapter.RestoAdapter
import com.example.berburumakanan.databinding.ActivityMainBinding
import com.example.berburumakanan.model.DataItem
import com.example.berburumakanan.model.ResponseResto
import com.example.berburumakanan.network.ApiConfig
import com.example.berburumakanan.ui.DetailActivity
import com.jaeger.library.StatusBarUtil
import com.semicolon.newsapp.helper.HelperClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var restoAdapter = RestoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        main()
    }

    private fun main(){
        supportActionBar?.hide()
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),0)
        HelperClass().hideBar(this)
        getData()

        binding.edtSearch.addTextChangedListener {
            restoAdapter.filter.filter(it)
        }
    }

    private fun getData(){
        val pdDialog = ProgressDialog.show(this,"Loading","Please Wait ....",true)
        ApiConfig.getInstanceRetrofit().resto().enqueue(object : Callback<ResponseResto>{
            override fun onResponse(call: Call<ResponseResto>, response: Response<ResponseResto>) {
                pdDialog.dismiss()
                if (response.isSuccessful){
                    if (response.body()?.data?.size!! > 0){
                        setData(response.body()?.data)
                    }else{
                        Toast.makeText(this@MainActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseResto>, t: Throwable) {
                pdDialog.dismiss()
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData(list: List<DataItem>?){
        list?.let {
            restoAdapter.setData(this,it)
            with(binding.rvFood){
                this.layoutManager = GridLayoutManager(this@MainActivity,2)
                this.adapter = restoAdapter
            }
        }
    }

    fun gotoDetail(data: DataItem){
        val i = Intent(this,DetailActivity::class.java)
        i.putExtra(DetailActivity.DATA,data)
        startActivity(i)
    }
}