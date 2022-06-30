package com.example.myplanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplanner.databinding.HomeScreenFragBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlin.coroutines.coroutineContext

class HomeScreenFragment: Fragment() {
    private lateinit var _adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = HomeScreenFragBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this, ModelFactory(Repo())).get(MainViewModel::class.java)
            viewModel.adapter.observe(viewLifecycleOwner, Observer { flow ->
                CoroutineScope(Dispatchers.Main).launch {
                    flow.collect { list ->
                        _adapter = ListAdapter(list)
                        binding.taskRecyclerview.apply {
                            layoutManager = LinearLayoutManager(binding.root.context)
                            adapter = _adapter
                        }
                    }
                    Log.d("Test", "after flow collect ")
                }
            })

        binding.include.addBtn.setOnClickListener{
            viewModel.addTask(binding.include.titleEt.text.toString())
        }


        return binding.root
    }
}