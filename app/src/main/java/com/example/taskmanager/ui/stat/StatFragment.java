package com.example.taskmanager.ui.stat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taskmanager.App;
import com.example.taskmanager.R;

public class StatFragment extends Fragment {

    private StatViewModel statViewModel;
    private TextView completedTasksCountTextView;
    private TextView incompleteTasksCountTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stat, container, false);

        completedTasksCountTextView = root.findViewById(R.id.completed_tasks_count);
        incompleteTasksCountTextView = root.findViewById(R.id.incomplete_tasks_count);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statViewModel = new ViewModelProvider(this).get(StatViewModel.class);

        statViewModel.getCompleteTaskCount().observe(getViewLifecycleOwner(), count -> {
            completedTasksCountTextView.setText(String.valueOf(count));
        });

        statViewModel.getIncompleteTaskCount().observe(getViewLifecycleOwner(), count -> {
            incompleteTasksCountTextView.setText(String.valueOf(count));
        });

        // Добавляем обработчики нажатий
        completedTasksCountTextView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(StatFragment.this);
            navController.navigate(R.id.action_to_completed_tasks);
        });

        incompleteTasksCountTextView.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(StatFragment.this);
            navController.navigate(R.id.action_to_incomplete_tasks);
        });


        // Подписываемся на LiveData из DAO и вызываем updateTaskCounts
        App.getInstance().getTaskDao().getAllLiveData().observe(getViewLifecycleOwner(), tasks -> {
            statViewModel.updateTaskCounts(tasks);
        });
    }
}