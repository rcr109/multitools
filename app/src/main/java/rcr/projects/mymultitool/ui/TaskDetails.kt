package rcr.projects.mymultitool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import rcr.projects.mymultitool.databinding.ActivityTaskDetailsBinding
import rcr.projects.mymultitool.datasource.TaskDataSource

class TaskDetails : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(task)) {
            val id = intent.getIntExtra(task, 0)
            TaskDataSource.findById(id)?.let {
                binding.tvTitulo.text = "Detalhes da task ${it.id}"
                binding.tvIdContent.text = " ${it.id}"
                binding.tvTituloTaskContent.text = " ${it.title}"
                binding.tvDataContent.text = " ${it.date}"
                binding.tvHoraContent.text = " ${it.hour}"
                binding.tvDescricaoContent.text = "${it.description}"
                binding.fabReturn.setOnClickListener {
                    finish()
                }
            }
        }
    }



    companion object {
        var task = ""
    }

}