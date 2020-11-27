package com.dot.lid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dot.lid.R;

public class LanguageSpinnerAdapter extends ArrayAdapter<Integer> {

    public LanguageSpinnerAdapter(@NonNull Context context, @NonNull Integer[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.layout_select_language_spinner_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.languageSpinnerItemName);
        TextView indicator = convertView.findViewById(R.id.languageSpinnerIndicator);
        int resource = getItem(position);
        if (resource > 0) {
            indicator.setTextColor(Color.WHITE);
            indicator.setText(Html.fromHtml("&#9673;"));
            name.setText(resource);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.layout_select_language_spinner_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.languageSpinnerItemName);
        TextView indicator = convertView.findViewById(R.id.languageSpinnerIndicator);
        int resource = getItem(position);
        if (resource > 0) {
            indicator.setTextColor(getContext().getResources().getColor(R.color.color_app_bar));
            indicator.setText(Html.fromHtml("&#9675;"));
            name.setText(resource);
        }
        return convertView;
    }
}
