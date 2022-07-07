package com.example.myplanner

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repo: Repo): ViewModel() {
    val adapter = liveData {
        emit(repo.readList())
    }


    fun addTask(newTask: Task){
        repo.addTask(newTask)
    }

    fun updateTask(_task: Task){
        repo.updateTask(_task)
    }
}