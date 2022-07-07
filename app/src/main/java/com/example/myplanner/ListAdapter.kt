package com.example.myplanner

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplanner.databinding.ItemLayoutBinding

class ListAdapter(private val list: ArrayList<Task>,
                  private val listener: OnItemClickListener): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = list[position]
        holder.itemBinding.taskTv.text = task.name
        Log.d("Test", "onBindViewHolder: ${task}")
        holder.itemBinding.completedCheckbox.isChecked = task.isCompleted
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
            init {
                itemBinding.completedCheckbox.setOnClickListener(this)
            }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}