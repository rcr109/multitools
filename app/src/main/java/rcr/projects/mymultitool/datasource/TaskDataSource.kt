package rcr.projects.mymultitool.datasource

import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.model.Task
import rcr.projects.mymultitool.ui.MainActivity


object TaskDataSource {

    fun insertTask(task: Task) {
        if (task.id == 0) {
            MultiApplication.instance.helperDB?.criarTarefa(task)
        } else {
            MultiApplication.instance.helperDB?.excluirTarefa(task)
            MultiApplication.instance.helperDB?.criarTarefa(task)
        }
    }


    fun findById(taskId: Int) = MultiApplication.instance.helperDB?.selecionarTarefa(taskId)

    fun findByDate(date: String) = MultiApplication.instance.helperDB?.filtrarPorData(date)

    fun findTasksDate(date: String) {
        val lista = MultiApplication.instance.helperDB?.buscarTarefas(date)
        MainActivity.adapter.submitList(lista)

    }

    fun deleteTask(task: Task) {
        MultiApplication.instance.helperDB?.excluirTarefa(task)
    }
}