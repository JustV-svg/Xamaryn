package com.example.xamaryn.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.xamaryn.data.XamarynDatabase
import com.example.xamaryn.data.XamarynDataRepository
import com.example.xamaryn.domain.AddXamarynUserCase
import com.example.xamaryn.domain.Xamaryn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class XamarynViewModel(application: Application): AndroidViewModel(application){
    private val db = Room.databaseBuilder(
        application,
        XamarynDatabase::class.java,
        "my_todo_db"
    ).build()

    private val XamarynRepository = XamarynDataRepository(db.XamarynDao())
    private val AddXamarynUserCase = AddXamarynUserCase(XamarynRepository)

    open val Xamaryn = XamarynRepository.getTodos().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addTodo(title: String) {
        viewModelScope.launch {
            AddXamarynUserCase.execute(title)
        }
    }

    fun toggleTodoDone(xamaryn: Xamaryn){
        viewModelScope.launch {
            XamarynRepository.updateTodo(xamaryn = xamaryn.copy(isDone = !xamaryn.isDone ))
        }
    }

    fun editTodo(xamaryn: Xamaryn, newTitle: String){
        viewModelScope.launch {
            if (newTitle.isNotBlank()) {
                XamarynRepository.updateTodo(xamaryn.copy(title = newTitle))
            }
        }
    }

    fun adiosLilyDelete(xamaryn: Xamaryn){
        viewModelScope.launch {
            XamarynRepository.deleteTodo(xamaryn)
        }
    }
}