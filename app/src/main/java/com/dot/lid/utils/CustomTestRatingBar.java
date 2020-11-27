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

public class CustomTestRatingBar extends ConstraintLayout {
    private ImageView smallImage;
    private ImageView mediumImage;
    private ImageView largeImage;
    private TextView ratingName;

    public CustomTestRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_test_achievement_item, this, true);
        smallImage = view.findViewById(R.id.smallImage);
        mediumImage = view.findViewById(R.id.mediumImage);
        largeImage = view.findViewById(R.id.largeImage);
        ratingName = view.findViewById(R.id.ratingNameTV);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomTestRatingBar, 0, 0);

        try {
            int smallImageResource = typedArray.getResourceId(R.styleable.CustomTestRatingBar_setSmallImage, R.drawable.ic_star_white);
            int mediumImageResource = typedArray.getResourceId(R.styleable.CustomTestRatingBar_setMediumImage, R.drawable.ic_star_white);
            int largeImageResource = typedArray.getResourceId(R.styleable.CustomTestRatingBar_setLargeImage, R.drawable.ic_star_white);
            int ratingNameResource = typedArray.getResourceId(R.styleable.CustomTestRatingBar_setTestRatingName, R.string.bronze);

            ratingName.setText(ratingNameResource);
            smallImage.setImageResource(smallImageResource);
            mediumImage.setImageResource(mediumImageResource);
            largeImage.setImageResource(largeImageResource);
        } finally {
            typedArray.recycle();
        }
    }

    public void setSmallImage(int resource) {
        smallImage.setImageResource(resource);
    }

    public void setMediumImage(int resource) {
        mediumImage.setImageResource(resource);
    }

    public void setLargeImage(int resource) {
        largeImage.setImageResource(resource);
    }

    public void setRatingName(int resource) {
        ratingName.setText(resource);
    }
}
