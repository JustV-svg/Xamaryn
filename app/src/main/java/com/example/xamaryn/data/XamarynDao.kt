package com.example.xamaryn.data
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface XamarynDao {
    @Query("SELECT * FROM mytodos")
    fun getAllTodos(): Flow<List<XamarynEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(xama: XamarynEntity)

    @Update
    suspend fun update(xama: XamarynEntity)

    @Delete
    suspend fun delete(xama: XamarynEntity)
}