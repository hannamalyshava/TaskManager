package com.example.taskmanager.screens.details;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.taskmanager.App;
import com.example.taskmanager.R;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.ui.CategorySpinnerAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_TASK = "TaskDetailsActivity.EXTRA_TASK";
    private Task task;
    private EditText editText;
    private TextView dateTextView;
    private Spinner categorySpinner;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void start(Activity caller, Task task) {
        Intent intent = new Intent(caller, TaskDetailsActivity.class);
        if (task != null) {
            intent.putExtra(EXTRA_TASK, task);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        EditText editText = findViewById(R.id.text);
        editText.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        initToolbar();
        initViews();
        setupCategorySpinner();
    }

    private void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setTitle(getString(R.string.task_details_title));
    }

    private void initViews() {
        editText = findViewById(R.id.text);
        dateTextView = findViewById(R.id.date);
        Button saveTask = findViewById(R.id.save_task);
        categorySpinner = findViewById(R.id.category_spinner);

        if (getIntent().hasExtra(EXTRA_TASK)) {
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            editText.setText(task.getText());
            dateTextView.setText(task.getDate());
        } else {
            task = new Task();
        }

        dateTextView.setOnClickListener(v -> showDatePickerDialog());
        saveTask.setOnClickListener(v -> saveTask());
    }

    private void setupCategorySpinner() {
        String[] categories = {"Без категории", "Личное", "Работа", "Учёба"};
        int[] colors = {
                R.color.grey,  // Без категории
                R.color.rose,  // Личное
                R.color.blue,  // Работа
                R.color.green  // Учёба
        };

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, categories, colors);
        categorySpinner.setAdapter(adapter);


        if (task.getCategory() != null) {
            int selectedIndex = Arrays.asList(categories).indexOf(task.getCategory());
            if (selectedIndex >= 0) {
                categorySpinner.setSelection(selectedIndex);
            }
        }

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setCategory(categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    dateTextView.setText(selectedDate);
                    task.setDate(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void saveTask() {
        if (editText.getText().length() == 0) {
            Toast.makeText(this, "Введите текст задачи", Toast.LENGTH_SHORT).show();
            return;
        }

        task.setText(editText.getText().toString());
        task.setCompleted(false);
        task.setTimestamp(System.currentTimeMillis());

        executor.submit(() -> {
            if (getIntent().hasExtra(EXTRA_TASK)) {
                App.getInstance().getTaskDao().update(task);
            } else {
                App.getInstance().getTaskDao().insert(task);
            }

            runOnUiThread(() -> {
                Toast.makeText(TaskDetailsActivity.this,
                        "Задача сохранена", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}