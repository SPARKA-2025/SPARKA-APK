package com.example.sparka_v0.ui.splashscreen.login

import androidx.lifecycle.ViewModel
import com.example.sparka_v0.data.retrofit.ApiConfig
import com.example.sparka_v0.data.retrofit.response.LoginRequest
import com.example.sparka_v0.data.retrofit.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val request = LoginRequest(email, password)
        ApiConfig.api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.access_token ?: ""
                    onResult(true, token)
                } else {
                    onResult(false, "Login gagal: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, "Error: ${t.message}")
            }
        })
    }
}