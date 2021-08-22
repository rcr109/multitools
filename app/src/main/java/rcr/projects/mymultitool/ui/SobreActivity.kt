package rcr.projects.mymultitool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import rcr.projects.mymultitool.R
import rcr.projects.mymultitool.databinding.ActivityMainBinding
import rcr.projects.mymultitool.databinding.ActivitySobreBinding

class SobreActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySobreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertListeners()
    }

    private fun insertListeners(){
        binding.fabHome.setOnClickListener {
            finish()
        }
    }




}