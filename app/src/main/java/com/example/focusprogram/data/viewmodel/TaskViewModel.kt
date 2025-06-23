package com.example.focusprogram.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusprogram.data.model.Task
import com.example.focusprogram.data.storage.TaskStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val context = application.applicationContext

    init {
        viewModelScope.launch {
            TaskStore.getTasksFlow(context).collect {
                _tasks.value = it
            }
        }
    }

    private fun saveTasks() {
        viewModelScope.launch {
            TaskStore.saveTasks(context, _tasks.value)
        }
    }

    fun toggleTaskDone(taskId: Int) {
        _tasks.value = _tasks.value.map {
            if (it.id == taskId) it.copy(isDone = !it.isDone) else it
        }
        saveTasks()
    }

    fun removeTask(taskId: Int) {
        _tasks.value = _tasks.value.filter { it.id != taskId }
        saveTasks()
    }

    fun addTask(title: String) {
        val newId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(newId, title)
        _tasks.value = _tasks.value + newTask
        saveTasks()
    }
}
