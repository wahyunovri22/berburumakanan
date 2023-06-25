package com.example.berburumakanan.model

import com.google.gson.annotations.SerializedName

data class ActionModel(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("fullname")
	val fullname: String? = null
)
