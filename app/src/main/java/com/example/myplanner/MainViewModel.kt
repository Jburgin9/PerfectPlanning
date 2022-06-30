package com.example.myplanner

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repo: Repo): ViewModel() {
    val adapter = liveData(Dispatchers.IO){
        emit(repo.readList())
    }


    fun addTask(new: String){
        repo.addTask(new)
    }
}