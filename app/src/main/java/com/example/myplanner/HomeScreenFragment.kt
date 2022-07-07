package com.example.myplanner

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplanner.databinding.HomeScreenFragBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import java.util.*
import kotlin.coroutines.coroutineContext

class HomeScreenFragment: Fragment(), ListAdapter.OnItemClickListener{
    private lateinit var _adapter: ListAdapter
    private var _list = ArrayList<Task>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeScreenFragBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, ModelFactory(Repo())).get(MainViewModel::class.java)
        viewModel.adapter.observe(viewLifecycleOwner, Observer { flow ->
                lifecycleScope.launch {
                    flow.collect { list ->
                        _list = list
                        _adapter = ListAdapter(_list, this@HomeScreenFragment)
                        binding.taskRecyclerview.apply {
                            layoutManager = LinearLayoutManager(binding.root.context)
                            adapter = _adapter
                        }
                    }
                }
            })
        binding.include.addBtn.setOnClickListener{
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_homeScreenFragment_to_addTaskFragment)
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val task = _list[position]
        task.isCompleted = !task.isCompleted
        Log.d("Test", "onItemClick: ${task.isCompleted}")
        viewModel.updateTask(task)
        _adapter.notifyItemChanged(position)
    }

}