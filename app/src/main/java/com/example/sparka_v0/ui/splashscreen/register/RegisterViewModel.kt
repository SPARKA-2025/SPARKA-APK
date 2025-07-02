package com.example.sparka_v0.ui.splashscreen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sparka_v0.data.retrofit.ApiConfig
import com.example.sparka_v0.data.retrofit.response.RegisterRequest
import com.example.sparka_v0.data.retrofit.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    fun registerUser(
        nama: String,
        email: String,
        password: String,
        phone: String,
        alamat: String,
        platNomor: String?,
        onResult: (Boolean, String) -> Unit
    ) {
        val request = RegisterRequest(nama, email, password, phone, alamat, platNomor)
        Log.d("REGISTER_DEBUG", "Request: $request")

        ApiConfig.api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()?.access_token ?: ""
                    onResult(true, token)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("REGISTER_ERROR", errorMsg)
                    onResult(false, "Register gagal: $errorMsg")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("REGISTER_FAILURE", t.message ?: "Unknown failure")
                onResult(false, "Error: ${t.message}")
            }
        })
    }
}
