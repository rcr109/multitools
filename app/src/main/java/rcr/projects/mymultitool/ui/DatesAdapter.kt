package rcr.projects.mymultitool.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.DateSquaredBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.extensions.text
import rcr.projects.mymultitool.model.Task

class DatesAdapter : ListAdapter<Task, DatesAdapter.DatesViewHolder>(DiffCallback1()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
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

                val date = item.date
                val lista = TaskDataSource.findByDate(date)
                Log.e("LISTA", lista.toString())
                Log.e("DATA", item.date)

                MainActivity.filtrar_dados()



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

