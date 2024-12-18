package com.example.administradordetareas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.administradordetareas.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var databaseHelper: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = TaskDatabaseHelper(this)

        binding.saveTaskButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            if (title.isNotBlank()) {
                databaseHelper.insertTask(title, description)
                finish()
            }
        }
    }
}
