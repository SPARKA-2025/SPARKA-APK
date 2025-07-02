package com.example.sparka_v0.data.retrofit

import com.example.sparka_v0.data.retrofit.response.DetailParkirResponse
import com.example.sparka_v0.data.retrofit.response.FakultasResponse
import com.example.sparka_v0.data.retrofit.response.LoginRequest
import com.example.sparka_v0.data.retrofit.response.LoginResponse
import com.example.sparka_v0.data.retrofit.response.RegisterRequest
import com.example.sparka_v0.data.retrofit.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/fakultas")
    fun getFakultas(
        @Header("Authorization") token: String
    ): Call<FakultasResponse>

    @GET("api/slot-parkir/{id}/get-total-slot-parkirs")
    suspend fun getDetailParkir(
        @Path("id") id: Int
    ): DetailParkirResponse

}