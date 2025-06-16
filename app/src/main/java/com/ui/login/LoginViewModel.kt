package com.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.Repository
import com.data.response.LoginResponse
import kotlinx.coroutines.launch
import com.data.Result
import org.json.JSONException
import org.json.JSONObject

class LoginViewModel(private val repo: Repository): ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> =  _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    //rev
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginResult.value = Result.Loading
            val result = repo.login(email, password)
            _loginResult.value = result
            _isLoading.value = false
        }
    }

    fun parseErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.optString("pesan", "Unknown error")
        } catch (e: JSONException) {
            "Error parsing error message"
        }
    }

    fun checkToken(): Boolean {
        return repo.isTokenValid()
    }

    fun logout() {
        repo.logout()
    }
}