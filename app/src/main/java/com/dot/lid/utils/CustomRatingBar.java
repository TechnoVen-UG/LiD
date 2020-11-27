package com.dot.lid.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.dot.lid.R;

public class CustomRatingBar extends ConstraintLayout {

    private ImageView imageView;
    private TextView textView;

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_rating_bar, this, true);
        imageView = view.findViewById(R.id.ratingIC);
        textView = view.findViewById(R.id.ratingTV);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomRatingBar, 0, 0);

        try {
            int ratingName = typedArray.getResourceId(R.styleable.CustomRatingBar_setRatingName, R.string.bronze);
            int ratingResource = typedArray.getResourceId(R.styleable.CustomRatingBar_setImageResource, R.drawable.star_with_bg);

            imageView.setImageResource(ratingResource);
            textView.setText(ratingName);

        } finally {
            typedArray.recycle();
        }

    }

    public void setRatingName(int ratingName) {
        textView.setText(ratingName);
    }

    public void setRatingResource(int ratingResource) {
        imageView.setImageResource(ratingResource);
    }
}
