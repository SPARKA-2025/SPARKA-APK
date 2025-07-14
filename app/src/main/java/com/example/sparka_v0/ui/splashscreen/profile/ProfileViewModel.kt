package com.example.sparka_v0.ui.splashscreen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    fun updatePlatNomor(plat: String): LiveData<Result<GeneralResponse>> {
        return repository.updatePlat(plat)
    }
}