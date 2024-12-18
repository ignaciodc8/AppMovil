package com.example.administradordetareas

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.administradordetareas.databinding.ActivityTaskDetailsBinding

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailsBinding
    private lateinit var databaseHelper: TaskDatabaseHelper
    private var taskId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = TaskDatabaseHelper(this)

        // Obtiene el ID de la tarea del intent
        taskId = intent.getLongExtra("TASK_ID", -1)

        if (taskId == -1L) {
            Toast.makeText(this, "Tarea no encontrada", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Cargar los datos de la tarea
        val task = databaseHelper.getAllTasks().find { it.id == taskId }
        task?.let {
            binding.titleEditText.setText(it.title)
            binding.descriptionEditText.setText(it.description)
        }

        // Guardar los cambios en la tarea
        binding.saveTaskButton.setOnClickListener {
            val updatedTitle = binding.titleEditText.text.toString()
            val updatedDescription = binding.descriptionEditText.text.toString()

            if (updatedTitle.isNotBlank()) {
                val values = ContentValues().apply {
                    put(TaskDatabaseHelper.COLUMN_TITLE, updatedTitle)
                    put(TaskDatabaseHelper.COLUMN_DESCRIPTION, updatedDescription)
                }
                databaseHelper.writableDatabase.update(
                    TaskDatabaseHelper.TABLE_TASKS,
                    values,
                    "${TaskDatabaseHelper.COLUMN_ID}=?",
                    arrayOf(taskId.toString())
                )
                Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

