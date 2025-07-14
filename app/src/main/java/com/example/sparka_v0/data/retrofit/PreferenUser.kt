package com.data

import android.content.Context
import android.util.Log
import androidx.core.content.edit

internal class UserPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(id: Int, token: String, expiresIn : Int) {
        sharedPreferences.edit {
            putInt(ID, id)
            putString(TOKEN, token)
            putLong(EXPIRES_AT, System.currentTimeMillis() + expiresIn * 1000)
            putBoolean(IS_LOGIN, true)
        }
        Log.d("token", token)
    }

    fun getUser(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun getIdUser(): Int {
        return sharedPreferences.getInt(ID, -1)
    }

    fun isTokenExpired(): Boolean {
        val isLoggedIn = sharedPreferences.getBoolean(IS_LOGIN, false)
        val expiresAt = sharedPreferences.getLong(EXPIRES_AT, 0)
        return isLoggedIn && System.currentTimeMillis() < expiresAt
    }

    fun clearUser() {
        sharedPreferences.edit {
            remove(TOKEN)
            putBoolean(IS_LOGIN, false)
            remove("expires_in")
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val ID ="id_user"
        private const val TOKEN = "token"
        private const val EXPIRES_AT = "expires_at"
        private const val IS_LOGIN = "isLogin"
    }
}