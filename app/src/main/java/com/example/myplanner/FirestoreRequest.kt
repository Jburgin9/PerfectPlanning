package com.example.myplanner

import kotlinx.coroutines.flow.Flow

interface FirestoreRequest {
    suspend fun readList(): Flow<List<String>>
}