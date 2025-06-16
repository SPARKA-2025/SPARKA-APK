package com.ui.bookedform

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ViewModelFactory
import com.data.Result
import com.example.bottomnavyt.ReceiptActivity
import com.example.bottomnavyt.databinding.ActivityRegisterPlatBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Form : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPlatBinding
    private lateinit var bookingViewModel: FormViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPlatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        bookingViewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)

        setupListeners()
        observeViewModel()
        binding.backform.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupListeners() {

        val idParkir = intent.getIntExtra("SELECTED_SEAT_ID", -1)

        Log.d("FormActivity", "Selected Seat ID: $idParkir")
        binding.tempatParkirField.setText(idParkir.toString())

        binding.submitButton.setOnClickListener {
            val platNomor = binding.nomorPlatField.text.toString()
            val jenisMobil = binding.jenisMobilField.text.toString()
            val idSlot = binding.tempatParkirField.text.toString()

            bookingViewModel.bookSlot(
                platNomor = platNomor,
                jenisMobil = jenisMobil,
                idSlot = idSlot
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        bookingViewModel.bookingResponse.observe(this, Observer { result ->
            binding.progressBar.visibility = View.GONE

            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val bookedResponse = result.data
                    bookedResponse?.let { data->
                        val tanggalBooking = parseDate(result.data.data?.waktuBooking.toString())
                        val bookingBerakhir = parseDate(result.data.data?.waktuBookingBerakhir.toString())
                        val intent = Intent(this, ReceiptActivity::class.java).apply {
                            putExtra("NOMOR_TRANSAKSI", generateTransactionNumber())
                            putExtra("NOMOR_PLAT", binding.nomorPlatField.text.toString())
                            putExtra("NAMA_PEMESAN", binding.namaPemesanField.text.toString())
                            putExtra("JENIS_MOBIL", binding.jenisMobilField.text.toString())
                            putExtra("TEMPAT_PARKIR", binding.tempatParkirField.text.toString())
                            putExtra("TANGGAL", tanggalBooking)
                            putExtra("BOOKING_END", bookingBerakhir)
                        }
                        Toast.makeText(this, result.data.status, Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }
                }

                is Result.Error -> {
                    Toast.makeText(this, result.error.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        bookingViewModel.loading.observe(this, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        bookingViewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                val error = bookingViewModel.parseErrorMessage(it)
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun generateTransactionNumber(): String {
        return "TRX" + System.currentTimeMillis().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDate(dateTimeString: String): String {
        val localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

        return localDateTime.format(formatter)
    }
}
