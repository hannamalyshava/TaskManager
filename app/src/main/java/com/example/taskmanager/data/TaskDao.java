package com.example.taskmanager.data;

import androidx.room.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.taskmanager.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getAll();
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllLiveData();
    @Query("SELECT * FROM Task WHERE uid IN (:taskIds)")
    List<Task> loadAllByIds(int[] taskIds);
    @Query("SELECT * FROM Task WHERE uid = :uid LIMIT 1")
    Task findById(int uid);
    @Query("SELECT * FROM Task WHERE date = :date")
    List<Task> findByDate(String date); // Метод для поиска задач по дате
    @Query("SELECT * FROM Task WHERE date BETWEEN :startDate AND :endDate")
    List<Task> findByDateRange(String startDate, String endDate); // Метод для поиска задач в диапазоне дат
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);
    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

}