package rcr.projects.mymultitool.datasource

import rcr.projects.mymultitool.application.MultiApplication
import rcr.projects.mymultitool.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        if (task.id == 0) {
            MultiApplication.instance.helperDB?.criarTarefa(task)
        } else {


            // TODO MultiApplication.instance.helperDB?.atualizarTarefa(task)
            MultiApplication.instance.helperDB?.excluirTarefa(task)
            MultiApplication.instance.helperDB?.criarTarefa(task)
        }
    }


    fun findById(taskId: Int) = MultiApplication.instance.helperDB?.selecionarTarefa(taskId)



    fun deleteTask(task: Task) {
        MultiApplication.instance.helperDB?.excluirTarefa(task)
    }
}