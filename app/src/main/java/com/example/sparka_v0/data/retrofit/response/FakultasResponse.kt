package com.example.sparka_v0.data.retrofit.response

data class FakultasResponse(
    val status: String,
    val data: List<Fakultas>
)

data class Fakultas(
    val id: Int,
    val nama: String,
    val deskripsi: String,
    val image: String,
    val created_at: String,
    val updated_at: String
)