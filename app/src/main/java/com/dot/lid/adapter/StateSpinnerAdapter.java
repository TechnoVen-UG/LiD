package com.dot.lid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dot.lid.R;
import com.dot.lid.model.State;
import com.dot.lid.view.main.MainActivity;

import java.util.ArrayList;

public class StateSpinnerAdapter extends ArrayAdapter<State> {
    private static final String TAG = "sayed";

    public StateSpinnerAdapter(@NonNull Context context, ArrayList<State> stateArrayList) {
        super(context, 0, stateArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.layout_custom_state_spinner, parent, false);
        }
        TextView stateName = convertView.findViewById(R.id.stateNameTV);
        TextView indicator = convertView.findViewById(R.id.indicatorTV);
        ImageView flag = convertView.findViewById(R.id.stateFlagIV);
        State currentSate = getItem(position);

        indicator.setVisibility(View.GONE);

        if (currentSate != null) {
            if (position == 0) {
                stateName.setText(R.string.test);
                flag.setVisibility(View.GONE);
            } else {
                stateName.setText(currentSate.getName());
                flag.setImageResource(currentSate.getFlag());
            }
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater
                .from(getContext())
                .inflate(R.layout.layout_custom_state_spinner, parent, false);

        TextView stateName = convertView.findViewById(R.id.stateNameTV);
        TextView indicator = convertView.findViewById(R.id.indicatorTV);
        ImageView flag = convertView.findViewById(R.id.stateFlagIV);
        State currentSate = getItem(position);

        if (currentSate != null) {

            if (position == 0) {
                stateName.setText(currentSate.getName());
                flag.setVisibility(View.GONE);
                indicator.setVisibility(View.INVISIBLE);
            } else {
                stateName.setText(currentSate.getName());
                flag.setImageResource(currentSate.getFlag());
            }
            if (position == MainActivity.CURRENT_POSITION) {
                indicator.setTextColor(getContext().getResources().getColor(R.color.dark_gray));
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.color_spinner_background));
            }
        }
        parent.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.popup_background));
        return convertView;
    }
}
