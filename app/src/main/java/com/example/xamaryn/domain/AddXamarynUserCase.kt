package com.example.xamaryn.domain

class AddXamarynUserCase (private val xamarynRepository: XamarynRepository) {
    suspend fun execute(title: String){
        xamarynRepository.addTodo(Xamaryn(title = title, isDone = false))
    }
}