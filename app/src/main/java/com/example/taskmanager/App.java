package com.example.taskmanager;

import android.app.Application;

import androidx.room.Room;

import com.example.taskmanager.data.AppDatabase;
import com.example.taskmanager.data.TaskDao;

public class App extends Application {
    private AppDatabase database;
    private TaskDao taskDao;
    private static App instance;
    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance=this;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-bd-name")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        taskDao=database.taskDao();
    }


    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

}
