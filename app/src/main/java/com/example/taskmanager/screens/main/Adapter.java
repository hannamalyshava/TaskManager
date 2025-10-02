package com.example.taskmanager.screens.main;


import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import com.example.taskmanager.App;
import com.example.taskmanager.R;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.screens.details.TaskDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    private SortedList<Task> sortedList;

    public Adapter() {
        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (!o2.getIsCompleted() && o1.getIsCompleted()) {
                    return 1;
                }
                if (o2.getIsCompleted() && !o1.getIsCompleted()) {
                    return -1;
                }
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Task oldItem, Task newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Task item1, Task item2) {
            return item1.getUid() == item2.getUid();
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }
    });

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }
    public void setItems(List<Task> tasks) {
        sortedList.replaceAll(tasks);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        TextView taskDate;
        CheckBox completed;
        View delete;
        Task task;
        boolean silentUpdate;

        View categoryColorLine;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.task_text); // Убедитесь, что ID совпадает с вашим макетом
            taskDate = itemView.findViewById(R.id.task_date); // Убедитесь, что ID совпадает с вашим макетом
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);
            categoryColorLine = itemView.findViewById(R.id.category_color_line);



            itemView.setOnClickListener(v -> TaskDetailsActivity.start((Activity) itemView.getContext(), task));

            delete.setOnClickListener(v -> App.getInstance().getTaskDao().delete(task));

            completed.setOnCheckedChangeListener((buttonView, checked) -> {
                if (!silentUpdate) {
                    task.setCompleted(checked);
                    App.getInstance().getTaskDao().update(task);
                }
                updateStrokeOut();
            });
        }

        public void bind(Task task) {
            this.task = task;
            taskText.setText(task.getText());
            taskDate.setText(task.getDate());

            silentUpdate = true;
            completed.setChecked(task.getIsCompleted());
            silentUpdate = false;
            int colorRes;
            String category = task.getCategory();
            if (category == null) {
                category = "Без категории";
            }

            switch (category) {
                case "Личное":
                    colorRes = R.color.rose;
                    break;
                case "Работа":
                    colorRes = R.color.blue;
                    break;
                case "Учёба":
                    colorRes = R.color.green;
                    break;
                default:
                    colorRes = R.color.grey;
                    break;
            }

            categoryColorLine.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), colorRes));


            updateStrokeOut();
        }

        private void updateStrokeOut() {
            if (task.getIsCompleted()) {
                taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                taskText.setPaintFlags(taskText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
    }
