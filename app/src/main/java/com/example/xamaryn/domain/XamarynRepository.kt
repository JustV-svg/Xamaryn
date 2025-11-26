package com.example.xamaryn.domain

import kotlinx.coroutines.flow.Flow

interface XamarynRepository {
    fun getTodos(): Flow<List<Xamaryn>>
    suspend fun addTodo(xamaryn: Xamaryn)
    suspend fun updateTodo(xamaryn: Xamaryn)
    suspend fun deleteTodo(xamaryn: Xamaryn)
}