package com.example.myplanner

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplanner.databinding.ItemLayoutBinding

class ListAdapter(private val list: List<String>?): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d("Test", "onBindViewHolder: ${list!![position]}")
        holder.itemBinding.taskTv.text = list!![position]
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ListViewHolder(val itemBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)
}