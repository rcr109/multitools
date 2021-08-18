package rcr.projects.mymultitool.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.databinding.ActivityMainBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()


        insertListeners()
        buscarDados()
        Log.e("TESTE RCR", buscarDados().toString())
        //DATA STORE
        //ROOM
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)

        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    override fun onResume() {
        super.onResume()
        buscarDados()
    }

    private fun updateList() {
        //val list = TaskDataSource.getList()
        //val list = filtrarDados()
        //val list = filtrarDados2()
        val list = buscarDados()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE

        adapter.submitList(list)
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

    private fun filtrarDados() :List<Task>{
        val busca = "TRT"
        var listafiltrada: List<Task> = mutableListOf()
        try {
            listafiltrada = MultiApplication.instance.helperDB?.filtrarTarefas(busca) ?: mutableListOf()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return listafiltrada
    }

    private fun filtrarDados2() :List<Task>{
        val busca = "Exemplo"
        var listafiltrada: List<Task> = mutableListOf()
        try {
            listafiltrada = MultiApplication.instance.helperDB?.filtrarTarefas2(busca) ?: mutableListOf()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return listafiltrada
    }

    private fun criarTarefa(task: Task){

    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }

}