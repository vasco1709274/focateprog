package com.example.focusprogram.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.focusprogram.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task(1, "Comprar pão", false),
            Task(2, "Fazer exercícios", true),
            Task(3, "Ler livro", false)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun toggleTaskDone(taskId: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == taskId) it.copy(isDone = !it.isDone) else it
        }
    }

    fun removeTask(taskId: Int) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
    }

    fun addTask(title: String) {
        val newId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(newId, title)
        _tasks.value = _tasks.value + newTask
    }
}
