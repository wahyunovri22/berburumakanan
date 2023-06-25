package com.example.berburumakanan.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.berburumakanan.R
import com.example.berburumakanan.databinding.ActivityRegisterBinding
import com.example.berburumakanan.model.ActionModel
import com.example.berburumakanan.network.ApiConfig
import com.jaeger.library.StatusBarUtil
import com.semicolon.newsapp.helper.HelperClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        main()
    }

    private fun main(){
        supportActionBar?.hide()
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white),0)
        HelperClass().hideBar(this)
        mainButton()
    }

    private fun mainButton(){
        binding.tvLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnLogin.setOnClickListener {
            if (validation()){
                register()
            }
        }
    }

    private fun validation():Boolean{
        if (binding.edtUsername.text.toString().isEmpty()){
            Toast.makeText(this,"Username harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.edtFullname.text.toString().isEmpty()){
            Toast.makeText(this,"Nama harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.edtPassword.text.toString().isEmpty()){
            Toast.makeText(this,"Password harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun register(){
        val pdDialog = ProgressDialog.show(this,"Loading","Please Wait ...",true)
        ApiConfig.getInstanceRetrofit().register(binding.edtUsername.text.toString(),
        binding.edtFullname.text.toString(),binding.edtPassword.text.toString()).enqueue(object : Callback<ActionModel>{
            override fun onResponse(call: Call<ActionModel>, response: Response<ActionModel>) {
                pdDialog.dismiss()
                if (response.isSuccessful){
                    if (response.body()?.error != true){
                        Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                        onBackPressedDispatcher.onBackPressed()
                        return
                    }
                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ActionModel>, t: Throwable) {
               pdDialog.dismiss()
                Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}