package com.example.sparka_v0.ui.splashscreen.parkiranmoment

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sparka_v0.R
import com.example.sparka_v0.data.retrofit.ApiConfig
import com.example.sparka_v0.data.retrofit.response.DetailParkirResponse
import kotlinx.coroutines.launch

class DetailParkirActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvJenis: TextView
    private lateinit var tvSlotKosong: TextView
    private lateinit var tvSlotTotal: TextView
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailparkir)

        tvNama = findViewById(R.id.tvNama)
        tvJenis = findViewById(R.id.tvJenis)
        tvSlotKosong = findViewById(R.id.SlotKosong)
        tvSlotTotal = findViewById(R.id.SlotTerisi)
        btnBack = findViewById(R.id.btnBack)

        val id = intent.getIntExtra("fakultas_id", -1)
        val nama = intent.getStringExtra("nama") ?: "-"
        val jenis = intent.getStringExtra("jenis") ?: "-"

        tvNama.text = nama
        tvJenis.text = jenis

        if (id != -1) {
            lifecycleScope.launch {
                try {
                    val response = ApiConfig.api.getDetailParkir(id)
                    updateSlotUI(response)
                } catch (_: Exception) {
                    Toast.makeText(this@DetailParkirActivity, "Gagal mengambil data parkiran", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "ID parkiran tidak valid", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateSlotUI(response: DetailParkirResponse) {
        tvSlotKosong.text = "${response.slot_kosong}/"
        tvSlotTotal.text = response.total_slot.toString()
    }
}
