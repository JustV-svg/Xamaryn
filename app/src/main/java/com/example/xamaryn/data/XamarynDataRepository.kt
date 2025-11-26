package com.example.xamaryn.data

import com.example.xamaryn.domain.Xamaryn
import com.example.xamaryn.domain.XamarynRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class XamarynDataRepository (private val dao: XamarynDao): XamarynRepository {
    override fun getTodos(): Flow<List<Xamaryn>> {
        return dao.getAllTodos().map { list ->
            list.map {entity ->
                Xamaryn(
                    id = entity.id,
                    title = entity.title,
                    isDone = entity.isDone
                )
            }
        }
    }

    override suspend fun addTodo(xamaryn: Xamaryn) {
        dao.insert(XamarynEntity(title = xamaryn.title,
            isDone = xamaryn.isDone))
    }

    override suspend fun updateTodo(xamaryn: Xamaryn) {
        dao.update(XamarynEntity(id = xamaryn.id, title = xamaryn.title, isDone = xamaryn.isDone))
    }

    override suspend fun deleteTodo(xamaryn: Xamaryn) {
        dao.delete(XamarynEntity(id = xamaryn.id, title = xamaryn.title, isDone = xamaryn.isDone))
    }

}