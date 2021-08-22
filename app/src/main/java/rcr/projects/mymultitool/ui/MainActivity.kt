package rcr.projects.mymultitool.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import rcr.projects.mymultitool.adapters.DatesAdapter
import rcr.projects.mymultitool.adapters.TaskListAdapter
import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.databinding.ActivityMainBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.model.Task

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTasks.adapter = adapter
        binding.rvDates.adapter = adapter2
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.fabNew.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
            updateList()
        }
        binding.fabHome.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.fabAll.setOnClickListener {
            updateList()
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
            updateList()
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        updateList()
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun updateList() {
        val list = buscarDados()
        val listdates = buscarDatas()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE
        adapter.submitList(list)
        adapter2.submitList(listdates)
    }

    private fun buscarDados() : List<Task>{
        val busca = ""
        var listafiltrada: List<Task> = mutableListOf()
        try {
            listafiltrada = MultiApplication.instance.helperDB?.buscarTarefas(busca) ?: mutableListOf()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return listafiltrada
    }

    private fun buscarDatas() : List<Task>{
        val busca = ""
        var listafiltradadatas: List<Task> = mutableListOf()
        try {
            listafiltradadatas = MultiApplication.instance.helperDB?.buscarDatas(busca) ?: mutableListOf()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return listafiltradadatas
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
        val adapter by lazy { TaskListAdapter() }
        val adapter2 by lazy { DatesAdapter() }
    }

}