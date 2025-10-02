package com.example.taskmanager.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.taskmanager.R;

public class CategorySpinnerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] categories;
    private final int[] colors;

    public CategorySpinnerAdapter(@NonNull Context context,
                                  String[] categories,
                                  int[] colors) {
        super(context, R.layout.spinner_category_item, categories);
        this.context = context;
        this.categories = categories;
        this.colors = colors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView != null ? convertView :
                LayoutInflater.from(context)
                        .inflate(R.layout.spinner_category_item, parent, false);

        TextView text = view.findViewById(R.id.category_text);
        View colorStripe = view.findViewById(R.id.color_stripe);

        text.setText(categories[position]);
        colorStripe.setBackgroundColor(ContextCompat.getColor(context, colors[position]));

        return view;
    }
}