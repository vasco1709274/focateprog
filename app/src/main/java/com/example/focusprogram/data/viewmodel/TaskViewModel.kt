package com.example.focusprogram.data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.focusprogram.data.model.Task

class TaskViewModel : ViewModel() {

    // Lista de tarefas reativa
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(title: String) {
        val newTask = Task(
            id = if (_tasks.isEmpty()) 1 else _tasks.maxOf { it.id } + 1,
            title = title
        )
        _tasks.add(newTask)
    }

    fun toggleTaskDone(taskId: Int) {
        val index = _tasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val task = _tasks[index]
            _tasks[index] = task.copy(isDone = !task.isDone)
        }
    }

    fun removeTask(taskId: Int) {
        _tasks.removeAll { it.id == taskId }
    }
}
