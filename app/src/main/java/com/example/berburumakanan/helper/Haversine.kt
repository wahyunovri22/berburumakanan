package com.example.berburumakanan.helper

class Haversine {
    private val RADIUS_BUMI = 6371 //km

    fun hitungJarak(
        latUser: Double, longUser: Double,
        latTujuan: Double, longTujuan: Double
    ): Double {
        var latUser = latUser
        var latTujuan = latTujuan
        val dLat = Math.toRadians(latTujuan - latUser)
        //        dLat = latTujuan-latUser;
        val dLong = Math.toRadians(longTujuan - longUser)
        //        dLong = longTujuan - longUser;
        latUser = Math.toRadians(latUser)
        latTujuan = Math.toRadians(latTujuan)
        val a = haversin(dLat) + Math.cos(latUser) * Math.cos(latTujuan) * haversin(dLong)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return RADIUS_BUMI * c // <-- d
    }

    fun haversin(`val`: Double): Double {
        return Math.pow(Math.sin(`val` / 2), 2.0)
    }
}