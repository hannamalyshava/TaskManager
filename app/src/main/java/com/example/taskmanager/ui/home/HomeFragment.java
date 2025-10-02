package com.example.taskmanager.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.R;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.screens.details.TaskDetailsActivity;
import com.example.taskmanager.screens.main.Adapter;
import com.example.taskmanager.screens.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private MainViewModel mainViewModel;

    private List<Task> allTasks = new ArrayList<>(); // Все задачи
    private String selectedCategory = "Все"; // Текущая категория


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.list);
        Button newTaskButton = view.findViewById(R.id.new_task);

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getTaskLiveData().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                allTasks = tasks;
                applyFilter(); // Применить фильтр при получении новых данных
            }
        });

        // Обработчики кнопок фильтра
        view.findViewById(R.id.filter_all).setOnClickListener(v -> {
            selectedCategory = "Все";
            applyFilter();
        });

        view.findViewById(R.id.filter_personal).setOnClickListener(v -> {
            selectedCategory = "Личное";
            applyFilter();
        });

        view.findViewById(R.id.filter_work).setOnClickListener(v -> {
            selectedCategory = "Работа";
            applyFilter();
        });

        view.findViewById(R.id.filter_study).setOnClickListener(v -> {
            selectedCategory = "Учёба";
            applyFilter();
        });
        Button filterAll = view.findViewById(R.id.filter_all);
        Button filterPersonal = view.findViewById(R.id.filter_personal);
        Button filterWork = view.findViewById(R.id.filter_work);
        Button filterStudy = view.findViewById(R.id.filter_study);

        View.OnClickListener categoryClickListener = btn -> {
            Button clickedButton = (Button) btn;
            selectedCategory = clickedButton.getText().toString();
            applyFilter();
            animateCategorySelection(clickedButton, filterAll, filterPersonal, filterWork, filterStudy);
        };

        filterAll.setOnClickListener(categoryClickListener);
        filterPersonal.setOnClickListener(categoryClickListener);
        filterWork.setOnClickListener(categoryClickListener);
        filterStudy.setOnClickListener(categoryClickListener);
        // Кнопка добавления задачи
        newTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), TaskDetailsActivity.class);
            startActivity(intent);
        });

        return view;
    }
    private void animateCategorySelection(Button selected, Button... allButtons) {
        for (Button button : allButtons) {
            int fromColor = Color.TRANSPARENT;

            if (button.getBackgroundTintList() != null) {
                fromColor = button.getBackgroundTintList().getDefaultColor();
            }

            int toColor;
            String text = button.getText().toString();

            if (button == selected) {
                switch (text) {
                    case "Личное":
                        toColor = getResources().getColor(R.color.rose);
                        break;
                    case "Работа":
                        toColor = getResources().getColor(R.color.blue);
                        break;
                    case "Учёба":
                        toColor = getResources().getColor(R.color.green);
                        break;
                    default:
                        toColor = getResources().getColor(R.color.grey);
                        break;
                }
            } else {
                toColor = getResources().getColor(R.color.white);
            }

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
            colorAnimation.setDuration(300);
            colorAnimation.addUpdateListener(animator -> {
                int animatedColor = (int) animator.getAnimatedValue();
                button.setBackgroundTintList(ColorStateList.valueOf(animatedColor));
            });
            colorAnimation.start();
        }
    }
    private void applyFilter() {
        if (selectedCategory.equals("Все")) {
            adapter.setItems(allTasks);
        } else {
            List<Task> filtered = new ArrayList<>();
            for (Task task : allTasks) {
                if (selectedCategory.equals(task.getCategory())) {
                    filtered.add(task);
                }
            }
            adapter.setItems(filtered);
        }
    }
}
