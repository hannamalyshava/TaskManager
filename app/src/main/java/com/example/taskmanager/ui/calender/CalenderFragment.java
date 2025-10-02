package com.example.taskmanager.ui.calender;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.App;
import com.example.taskmanager.databinding.FragmentCalenderBinding;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.screens.main.Adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CalenderFragment extends Fragment {

    private FragmentCalenderBinding binding;
    private Adapter adapter;
    private TextView selectedDateTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalenderViewModel calenderViewModel =
                new ViewModelProvider(this).get(CalenderViewModel.class);

        binding = FragmentCalenderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        selectedDateTextView = binding.textSelectedDate;
        RecyclerView recyclerView = binding.recyclerView;

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = formatDate(year, month, dayOfMonth);
            selectedDateTextView.setText(selectedDate);
            loadTasksForDate(selectedDate);
        });

        return root;
    }

    private void loadTasksForDate(String date) {
        new Thread(() -> {
            List<Task> tasks = App.getInstance().getTaskDao().findByDate(date);
            getActivity().runOnUiThread(() -> adapter.setItems(tasks));
        }).start();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        // Формат даты "год-месяц-день"
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
