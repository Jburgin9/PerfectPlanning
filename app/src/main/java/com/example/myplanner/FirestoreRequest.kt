package com.example.myplanner

import kotlinx.coroutines.flow.Flow

interface FirestoreRequest {
    fun readList(): Flow<List<Task>>
}