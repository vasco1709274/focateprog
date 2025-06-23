
package com.example.focusprogram.data.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.focusprogram.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val Context.taskDataStore by preferencesDataStore("tasks_prefs")

object TaskStore {
    private val TASKS_KEY = stringPreferencesKey("tasks")

    fun getTasksFlow(context: Context): Flow<List<Task>> =
        context.taskDataStore.data.map { preferences ->
            preferences[TASKS_KEY]?.let {
                try {
                    Json.decodeFromString<List<Task>>(it)
                } catch (e: Exception) {
                    emptyList()
                }
            } ?: emptyList()
        }

    suspend fun saveTasks(context: Context, tasks: List<Task>) {
        val json = Json.encodeToString(tasks)
        context.taskDataStore.edit { preferences ->
            preferences[TASKS_KEY] = json
        }
    }
}
