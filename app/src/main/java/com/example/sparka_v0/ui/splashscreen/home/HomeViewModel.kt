package com.example.sparka_v0.ui.splashscreen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sparka_v0.data.retrofit.ApiConfig
import com.example.sparka_v0.data.retrofit.response.Fakultas
import com.example.sparka_v0.data.retrofit.response.FakultasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    val fakultasList = MutableLiveData<List<Fakultas>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getFakultas(token: String) {
        isLoading.value = true
        ApiConfig.api.getFakultas("Bearer $token")
            .enqueue(object : Callback<FakultasResponse> {
                override fun onResponse(
                    call: Call<FakultasResponse>,
                    response: Response<FakultasResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        fakultasList.value = response.body()?.data ?: emptyList()
                    } else {
                        errorMessage.value = "Gagal ambil data: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<FakultasResponse>, t: Throwable) {
                    isLoading.value = false
                    errorMessage.value = "Error: ${t.message}"
                }
            })
    }
}