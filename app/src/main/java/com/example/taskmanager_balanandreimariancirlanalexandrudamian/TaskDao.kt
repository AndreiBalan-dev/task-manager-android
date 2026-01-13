package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks ORDER BY title COLLATE NOCASE ASC")
    suspend fun getAllByTitle(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): TaskEntity?

    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

}
