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
import rcr.projects.mymultitool.model.Task
import rcr.projects.mymultitool.ui.AddTaskActivity
import rcr.projects.mymultitool.ui.MainActivity
import rcr.projects.mymultitool.ui.TaskDetails
import rcr.projects.mymultitool.ui.TaskDetails.Companion.task


class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {
    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = ItemTaskBinding.inflate(inflater, parent, false)
        val binding = ItemTaskCardBinding.inflate(inflater, parent, false)
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
        private val binding: ItemTaskCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDate.text = "${item.date} - ${item.hour}"
            binding.tvDescription.text = item.description
            binding.tvId.text = item.id.toString()
            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
            binding.clItem.setOnClickListener {
                itemClicado(it, item.id)
            }

        }

        private fun showPopup(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}