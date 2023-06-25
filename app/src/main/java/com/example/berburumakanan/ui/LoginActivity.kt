package com.example.berburumakanan.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.berburumakanan.MainActivity
import com.example.berburumakanan.R
import com.example.berburumakanan.databinding.ActivityLoginBinding
import com.example.berburumakanan.model.ActionModel
import com.example.berburumakanan.network.ApiConfig
import com.jaeger.library.StatusBarUtil
import com.semicolon.newsapp.helper.HelperClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        main()
    }

    private fun main(){
        supportActionBar?.hide()
        StatusBarUtil.setColor(this,ContextCompat.getColor(this,R.color.white),0)
        HelperClass().hideBar(this)
        mainButton()
    }

    private fun mainButton(){
        binding.tvBuat.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {
            if (validation()){
                login()
            }
        }
    }

    private fun validation():Boolean{
        if (binding.edtUsername.text.toString().isEmpty()){
            Toast.makeText(this,"Username harus diisi",Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.edtPassword.text.toString().isEmpty()){
            Toast.makeText(this,"Password harus diisi",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun login(){
        val pdDialog = ProgressDialog.show(this,"Loading","Please Wait ....",true)
        ApiConfig.getInstanceRetrofit().login(binding.edtUsername.text.toString(),
        binding.edtPassword.text.toString()).enqueue(object : Callback<ActionModel>{
            override fun onResponse(call: Call<ActionModel>, response: Response<ActionModel>) {
                pdDialog.dismiss()
                if (response.isSuccessful){
                    val data = response.body()
                    if (data?.error != true){
                        Toast.makeText(this@LoginActivity, data?.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, data.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ActionModel>, t: Throwable) {
                pdDialog.dismiss()
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}