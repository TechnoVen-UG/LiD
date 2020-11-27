package com.dot.lid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dot.lid.R;
import com.dot.lid.view.training.TrainingActivity;

public class HistorySpinnerAdapter extends ArrayAdapter<Integer> {
    public HistorySpinnerAdapter(@NonNull Context context, @NonNull Integer[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.layout_category_spinner_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.categorySpinnerItemName);
        TextView indicator = convertView.findViewById(R.id.categorySpinnerIndicator);
        int resource = getItem(position);
        if (resource > 0) {
            name.setText(resource);
            indicator.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater
                .from(getContext())
                .inflate(R.layout.layout_category_spinner_item, parent, false);

        TextView name = convertView.findViewById(R.id.categorySpinnerItemName);
        TextView indicator = convertView.findViewById(R.id.categorySpinnerIndicator);
        int resource = getItem(position);
        if (resource > 0) {
            if (position == 0) {
                indicator.setVisibility(View.INVISIBLE);
            }
            name.setText(resource);
            if (position == TrainingActivity.HISTORY_CURRENT_POSITION) {
                indicator.setTextColor(getContext().getResources().getColor(R.color.dark_gray));
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color_spinner_background));
            }
        }
        parent.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.popup_background));
        return convertView;
    }
}
