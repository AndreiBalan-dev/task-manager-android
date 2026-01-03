package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager_balanandreimariancirlanalexandrudamian.databinding.ItemTaskBinding

data class Task(
    val id: Long,
    val title: String,
    val description: String
)

class TaskAdapter(
    private val tasks: List<Task>,
    private val onClick: (Task) -> Unit,
    private val onLongClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.taskTitle.text = task.title
        holder.binding.taskDescription.text = task.description

        holder.binding.root.setOnClickListener { onClick(task) }
        holder.binding.root.setOnLongClickListener {
            onLongClick(task)
            true
        }
    }

    override fun getItemCount() = tasks.size
}