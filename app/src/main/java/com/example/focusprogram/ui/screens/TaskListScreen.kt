package com.example.focusprogram.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.focusprogram.data.model.Task
import com.example.focusprogram.data.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel = viewModel() // Usa ViewModel corretamente
) {
    val tasks = taskViewModel.tasks

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Lista de Tarefas",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { taskViewModel.toggleTaskDone(task.id) },
                    onDelete = { taskViewModel.removeTask(task.id) }
                )
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Apenas adiciona uma tarefa de teste para já
                taskViewModel.addTask("Nova Tarefa")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Tarefa")
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onCheckedChange() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (task.isDone)
                    MaterialTheme.colorScheme.onSurfaceVariant
                else
                    MaterialTheme.colorScheme.onSurface
            )
        }
        Button(onClick = onDelete) {
            Text("Apagar")
        }
    }
}
