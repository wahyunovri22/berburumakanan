package com.example.berburumakanan.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseResto(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("kode")
	val kode: Int? = null
):Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("cover")
	val cover: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("web")
	val web: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longi")
	val longi: String? = null,

	@field:SerializedName("open")
	val open: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
):Parcelable
