package rcr.projects.mymultitool.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rcr.projects.mymultitool.databinding.DateSquaredTwoBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.model.Task

class DatesAdapter : ListAdapter<Task, DatesAdapter.DatesViewHolder>(DiffCallback1()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DateSquaredTwoBinding.inflate(inflater, parent, false)
        return DatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun itemClicado(it : View, data: String) {
        TaskDataSource.findTasksDate(data)
    }

    inner class DatesViewHolder(
        private val binding: DateSquaredTwoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.txtSquare.text = nomeMes(item.date.substring(3, 5))
            binding.txtSquare1.text = item.date.substring(0, 2)
            binding.txtSquare2.text = item.date.substring(6, 10)

            binding.cvDates.setOnClickListener {
                val date = item.date
                itemClicado(it, date)
            }
        }
    }

    fun nomeMes(mes: String): String {
        val mes = mes
        var mesStr = ""
        when (mes) {
            "01" -> mesStr = "JAN"
            "02" -> mesStr = "FEV"
            "03" -> mesStr = "MAR"
            "04" -> mesStr = "ABR"
            "05" -> mesStr = "MAI"
            "06" -> mesStr = "JUN"
            "07" -> mesStr = "JUL"
            "08" -> mesStr = "AGO"
            "09" -> mesStr = "SET"
            "10" -> mesStr = "OUT"
            "11" -> mesStr = "NOV"
            "12" -> mesStr = "DEZ"
        }
        return mesStr
    }
}

class DiffCallback1 : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id}

