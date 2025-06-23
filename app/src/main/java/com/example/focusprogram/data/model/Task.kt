package com.example.focusprogram.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Int,
    val title: String,
    var isDone: Boolean = false
)
