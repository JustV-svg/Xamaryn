package com.example.xamaryn.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("mytodos")
data class XamarynEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false
)
