package com.example.taskmanager.ui.stat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.screens.main.Adapter;

public class IncompleteTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private StatViewModel statViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomplete_tasks, container, false);

        recyclerView = view.findViewById(R.id.recycler_incompleted);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        statViewModel = new ViewModelProvider(this).get(StatViewModel.class);
        statViewModel.getIncompleteTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.setItems(tasks);
        });

        return view;
    }
}
