package com.example.shreyastest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shreyastest.beanclasses.Images
import com.example.shreyastest.repository.MainActivityRepo

class MainActivityVm(application: Application) : AndroidViewModel(application) {
    private val mainActivityRepo: MainActivityRepo
    val imagesMutableLiveData: MutableLiveData<List<Images>>
        get() = mainActivityRepo.mutableLiveData

    fun setDispose() {
        mainActivityRepo.onDisponse()
    }

    fun searchWord(str: String?) {
        mainActivityRepo.getData(str)
    }

    init {
        mainActivityRepo = MainActivityRepo.getInstance(application)
    }
}