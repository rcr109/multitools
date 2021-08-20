package rcr.projects.mymultitool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.ActivityAddTaskBinding
import rcr.projects.mymultitool.databinding.ActivityTaskDetailsBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.extensions.text

class TaskDetails : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(AddTaskActivity.TASK_ID)) {
            val taskId = intent.getIntExtra(AddTaskActivity.TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tvTeste.text = it.description
            }
        }
    }
}