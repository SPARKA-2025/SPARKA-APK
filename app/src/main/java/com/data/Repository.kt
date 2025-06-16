package com.data

import android.content.Context
import android.util.Log
import com.data.response.*
import com.data.retrofit.ApiService
import retrofit2.HttpException


//rev semua wkwk
class Repository(private val apiService: ApiService, context: Context) {

    private val userPreferences = UserPreferences(context)

    // Validasi token
    fun isTokenValid(): Boolean {
        return !userPreferences.isTokenExpired()
    }

    fun logout() {
        userPreferences.clearUser()
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return safeCall {
            val response = apiService.login(email, password)
            userPreferences.saveUser(response.id, response.accessToken, response.expiresIn)
            Log.d("Repository", "Login successful: $response")
            response
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        address: String,
        noHp: String
    ): Result<LoginResponse> {
        return safeCall {
            val response = apiService.register(name, email, password, address, noHp)
            Log.d("Repository", "Register successful: $response")
            response
        }
    }

    suspend fun bookSlot(
        platNomor: String,
        jenisMobil: String,
        idSlot: String
    ): Result<BookedResponse> {
        return safeCall {
            val idUser = userPreferences.getIdUser()
            apiService.bookSlot(idUser, platNomor, jenisMobil, idSlot)
        }
    }

    suspend fun getSlot(id: Int): Result<SlotResponse> {
        return safeCall { apiService.getALlSlot(id) }
    }

    suspend fun getSlotBlok(id: Int): Result<BlokResponse> {
        return safeCall { apiService.getBlokTotal(id) }
    }

    suspend fun getSeatStatus(id: Int): Result<SeatStatusResponse> {
        return safeCall { apiService.getStatusDepanBelakang(id) }
    }

    suspend fun getListBooked(): Result<ListBookedResponse> {
        return safeCall {
            val id = userPreferences.getIdUser()
            apiService.getListBook(id)
        }
    }

    suspend fun getDetailBooked(id: Int): Result<DetailBookedResponse> {
        return safeCall { apiService.getDetailBooked(id) }
    }

    //Clean error handling
    private inline fun <T> safeCall(action: () -> T): Result<T> {
        return try {
            Result.Success(action())
        } catch (e: HttpException) {
            val message = e.response()?.errorBody()?.string() ?: "HTTP error"
            Log.e("Repository", "HttpException: $message", e)
            Result.Error(e, message)
        } catch (e: Exception) {
            Log.e("Repository", "Unexpected error", e)
            Result.Error(e, e.message ?: "Unknown error")
        }
    }
}