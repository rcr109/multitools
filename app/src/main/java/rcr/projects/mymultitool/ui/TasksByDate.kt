package rcr.projects.mymultitool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.adapters.TaskListAdapter
import rcr.projects.mymultitool.adapters.TasksByDateListAdapter
import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.databinding.ActivityTasksByDateBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.extensions.text
import rcr.projects.mymultitool.model.Task

class TasksByDate : AppCompatActivity() {
    private lateinit var binding: ActivityTasksByDateBinding
    private val adapter by lazy { TasksByDateListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksByDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTasksDate.adapter = adapter
        if (intent.hasExtra(data)) {
            val data = intent.getStringExtra(data)
            if (data != null) {
                var lista = filtrarPorData(data)
                adapter.submitList(lista)
                binding.tvTituloTasksDate.text = "Tarefas para $data = ${lista.size}"
            }
        }
        binding.fabHome.setOnClickListener {
            finish()
        }
    }

    private fun filtrarPorData(data: String) :List<Task>{
        val busca = data
        var listafiltrada: List<Task> = mutableListOf()
        try {
            listafiltrada = MultiApplication.instance.helperDB?.filtrarPorData(busca) ?: mutableListOf()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return listafiltrada
    }



    companion object {
        var data = ""
    }
}