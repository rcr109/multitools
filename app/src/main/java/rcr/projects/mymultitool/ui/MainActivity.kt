package rcr.projects.mymultitool.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.application.MultiApplication.Companion.instance
import rcr.projects.mymultitool.databinding.ActivityMainBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.model.Task
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    private val adapter2 by lazy { DatesAdapter() }
    private val adapter3 by lazy { TaskListAdapterFiltered()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        binding.rvDates.adapter = adapter2

        updateList()
        insertListeners()
        buscarDados()
        buscarDatas()
        filtrar_dados()

    }

    private fun insertListeners() {
        binding.fabNew.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)

        }

        binding.fabHome.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
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
        buscarDatas()
        filtrar_dados()
    }

    fun updateList() {
        //val list = TaskDataSource.getList()
        //val list = filtrarDados()
        //val list = filtrarDados2()
        val list = buscarDados()
        val list_dates = buscarDatas()
        binding.includeEmpty.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE

        adapter.submitList(list)
        adapter2.submitList(list_dates)
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

    private fun buscarDatas() : List<Task>{
        val busca = ""
        var listafiltrada: List<Task> = mutableListOf()
        try {
            listafiltrada = MultiApplication.instance.helperDB?.buscarDatas(busca) ?: mutableListOf()
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





    companion object {
        fun filtrar_dados() {
            Log.e("BUSCA", "BUSQUEI")



        }
        private const val CREATE_NEW_TASK = 1000
    }

}