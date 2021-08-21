package rcr.projects.mymultitool.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.ItemTaskCardBinding
import rcr.projects.mymultitool.databinding.ItemTaskCardResumedBinding
import rcr.projects.mymultitool.model.Task
import rcr.projects.mymultitool.ui.AddTaskActivity
import rcr.projects.mymultitool.ui.MainActivity
import rcr.projects.mymultitool.ui.TaskDetails
import rcr.projects.mymultitool.ui.TaskDetails.Companion.task

class TasksByDateListAdapter : ListAdapter<Task, TasksByDateListAdapter.TaskViewHolder>(DiffCallback3()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskCardResumedBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun itemClicado(it : View, id: Int) {
        val context: Context = it.getContext()
        val intent = Intent(context, TaskDetails::class.java)
        intent.putExtra(task, id)
        context.startActivity(intent)
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskCardResumedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDate.text = "${item.date} - ${item.hour}"
            binding.tvDescription.text = item.description
            binding.tvId.text = item.id.toString()
            binding.clItem.setOnClickListener {
                itemClicado(it, item.id)
            }
        }
    }
}

class DiffCallback3 : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}