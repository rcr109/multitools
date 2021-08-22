package rcr.projects.mymultitool.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.ActivityAddTaskBinding
import rcr.projects.mymultitool.datasource.TaskDataSource
import rcr.projects.mymultitool.extensions.format
import rcr.projects.mymultitool.extensions.text
import rcr.projects.mymultitool.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
                binding.tilDescription.text = it.description
                binding.tvTitulo.text = getString(R.string.txt_titulo_tela_add)
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }


        binding.fabCheck.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                day = binding.tilDate.text.substring(0,2),
                month = binding.tilDate.text.substring(3,5),
                year = binding.tilDate.text.substring(6,10),
                hour = binding.tilHour.text,
                description = binding.tilDescription.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.fabCancel.setOnClickListener {
            finish()
        }
    }


    companion object {
        const val TASK_ID = "task_id"
    }

}