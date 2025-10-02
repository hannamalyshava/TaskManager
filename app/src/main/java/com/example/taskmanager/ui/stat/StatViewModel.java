package com.example.taskmanager.ui.stat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmanager.App;
import com.example.taskmanager.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class StatViewModel extends ViewModel {

    private final MutableLiveData<Integer> completeTaskCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> incompleteTaskCount = new MutableLiveData<>(0);
    private final MutableLiveData<List<Task>> completedTasks = new MutableLiveData<>();
    private final MutableLiveData<List<Task>> incompleteTasks = new MutableLiveData<>();

    public LiveData<Integer> getCompleteTaskCount() {
        return completeTaskCount;
    }

    public LiveData<Integer> getIncompleteTaskCount() {
        return incompleteTaskCount;
    }

    public LiveData<List<Task>> getCompletedTasks() {
        return completedTasks;
    }

    public LiveData<List<Task>> getIncompleteTasks() {
        return incompleteTasks;
    }
    public StatViewModel() {
        App.getInstance().getTaskDao().getAllLiveData().observeForever(tasks -> {
            updateTaskCounts(tasks);
        });
    }

    public void updateTaskCounts(List<Task> tasks) {
        if (tasks == null) {
            completeTaskCount.setValue(0);
            incompleteTaskCount.setValue(0);
            return;
        }

        int completed = 0;
        int incomplete = 0;

        for (Task task : tasks) {
            if (task.getIsCompleted()) {  // Используем исправленный геттер
                completed++;
            } else {
                incomplete++;
            }
        }

        completeTaskCount.setValue(completed);
        incompleteTaskCount.setValue(incomplete);

        // Обновляем списки
        completedTasks.setValue(tasks.stream()
                .filter(Task::getIsCompleted)
                .collect(Collectors.toList()));

        incompleteTasks.setValue(tasks.stream()
                .filter(task -> !task.getIsCompleted())
                .collect(Collectors.toList()));
    }
}
