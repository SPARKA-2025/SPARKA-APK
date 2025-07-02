package com.example.sparka_v0.data.retrofit.response

data class DetailParkirResponse(
    val total_slot: Int,
    val slot_kosong: String,
    val slot_selesai: Int,
    val slots_dibooking: Int,
    val slot_terisi: Int
)