package com.example.myplanner

import java.util.*

data class Task(
    val name:String? = null,
    var isCompleted: Boolean = false,
    var dueDate: String? = null
)


