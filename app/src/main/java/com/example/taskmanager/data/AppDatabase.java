package com.example.taskmanager.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskmanager.model.Task;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

}
