package com.example.sparka_v0.data.retrofit.response
import com.google.gson.annotations.SerializedName


data class RegisterRequest(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("alamat")
    val alamat : String,

    @SerializedName("plat_nomor")
    val platNomor: String? = null

)