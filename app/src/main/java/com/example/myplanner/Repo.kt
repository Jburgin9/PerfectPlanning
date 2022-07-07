package com.example.myplanner

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log

class Repo: FirestoreRequest {
    private val firebaseInstance = FirebaseFirestore.getInstance()

    fun addTask(newTask: Task){
        val newMap = HashMap<String, String>()
        newMap.put("name", newTask.name!!)
        newMap.put("isCompleted", newTask.isCompleted.toString())
        newMap.put("dueDate", newTask.dueDate.toString())
        firebaseInstance.collection("tasks")
            .document().set(newMap)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Log.d("Test", "fail: ${it}")
            }
    }

    override fun readList(): Flow<ArrayList<Task>> = callbackFlow{
        val docRef = firebaseInstance.collection("tasks")
        docRef.get()
            .addOnSuccessListener {

                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        val subscription = docRef.addSnapshotListener{ snapshot, _ ->
            if(!snapshot!!.documents.isEmpty()){
                trySend(extractList(snapshot!!.documents))
            }
        }

        awaitClose{ subscription.remove() }
    }

    private fun extractList(snapshots: List<DocumentSnapshot>): ArrayList<Task>{
        var list = ArrayList<Task>()
        snapshots.forEach {
            list.add(it.toObject(Task::class.java)!!)
        }
        return list
    }

    private fun locateTaskId(_taskName: String): String{
        var id = ""
        val document = firebaseInstance.collection("tasks")
            .whereEqualTo("name", _taskName)
        document.get().addOnSuccessListener {
            for(doc in it){
                id = doc.id
            }
        }
        return id
    }

    fun updateTask(_task: Task){
        firebaseInstance.collection("tasks")
            .document("9jMHcnq6jWbEAT4F9ZzE")
            .update("isCompleted", _task.isCompleted)
            .addOnSuccessListener { Log.d("Test", "updateTask: success, ${_task.isCompleted}") }
            .addOnFailureListener { Log.d("Test", "updateTask: failure") }
    }

}