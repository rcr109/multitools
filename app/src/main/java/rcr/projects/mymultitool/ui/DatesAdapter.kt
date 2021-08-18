package rcr.projects.mymultitool.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.DateSquaredBinding
import rcr.projects.mymultitool.databinding.ItemTaskCardBinding
import rcr.projects.mymultitool.model.Task

class DatesAdapter : ListAdapter<Task, DatesAdapter.DatesViewHolder>(DiffCallback1()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = ItemTaskBinding.inflate(inflater, parent, false)
        val binding = DateSquaredBinding.inflate(inflater, parent, false)
        return DatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DatesViewHolder(
        private val binding: DateSquaredBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.txtSquare.text = item.date.toString()

            binding.txtSquare.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val ivMore = binding.txtSquare
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

class DiffCallback1 : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id}

