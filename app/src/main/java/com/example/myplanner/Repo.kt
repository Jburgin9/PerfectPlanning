package com.example.myplanner

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.math.log

class Repo: FirestoreRequest {
    private val firebaseInstance = FirebaseFirestore.getInstance()

    fun addTask(newTask: String){
        val newMap = hashMapOf(newTask.toString() to newTask)
        firebaseInstance.collection("tasks")
            .add(newMap)
            .addOnSuccessListener {
                Log.d("Test", "addTask: $it")
            }
            .addOnFailureListener {
                Log.d("Test", "fail: ${it}")
            }
    }

    override suspend fun readList(): Flow<List<String>> = callbackFlow{
        val docRef = firebaseInstance.collection("tasks")
        docRef.get()
            .addOnSuccessListener {
                val results = it.documents
                Log.d("Test", "readList: $results")
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        val subscription = docRef.addSnapshotListener{ snapshot, _ ->
            if(!snapshot!!.documents.isEmpty()){
                trySend(extractList(snapshot!!.documents))
            }
        }

        awaitClose{ subscription.remove()}
    }

    fun extractList(snapshots: List<DocumentSnapshot>): ArrayList<String>{
        val list = ArrayList<String>()
        snapshots.forEach {
            list.add(it.id)
        }
        return list
    }

}