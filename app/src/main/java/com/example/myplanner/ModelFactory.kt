package com.example.myplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelFactory(private val repo: Repo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            Repo::class.java
        ).newInstance(repo)
    }

}