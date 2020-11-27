package com.dot.lid.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dot.lid.R;
import com.dot.lid.model.Achievement;
import com.dot.lid.utils.CustomRatingBar;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private static final String TAG = "sayed";

    private List<Achievement> achievementList;

    public AchievementAdapter(List<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_practice_achievement_item, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        if (achievementList == null) return;
        Achievement achievement = achievementList.get(position);
        holder.mainCategory.setText(achievement.getMainCategory());
        holder.subCategory.setText(achievement.getSubCategory());
        holder.updateAchievement(achievement);
    }

    @Override
    public int getItemCount() {
        if (achievementList == null) return 0;
        else return achievementList.size();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {

        private TextView mainCategory;
        private TextView subCategory;
        private CustomRatingBar bronze, silver, gold, platinum;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            mainCategory = itemView.findViewById(R.id.mainCategory);
            subCategory = itemView.findViewById(R.id.subCategory);
            bronze = itemView.findViewById(R.id.ratingBronze);
            silver = itemView.findViewById(R.id.ratingSilver);
            gold = itemView.findViewById(R.id.ratingGold);
            platinum = itemView.findViewById(R.id.ratingPlatinum);
        }

        private void updateAchievement(Achievement data) {
            if (data.isHasBronze()) {
                Log.d(TAG, "updateAchievement: " + data.getName());
                bronze.setRatingResource(R.drawable.bronze_with_bg);
                silver.setRatingResource(R.drawable.star_with_bg);
                gold.setRatingResource(R.drawable.star_with_bg);
                platinum.setRatingResource(R.drawable.star_with_bg);
            } else if (data.isHasSilver()) {
                Log.d(TAG, "updateAchievement: " + data.getName());
                bronze.setRatingResource(R.drawable.bronze_with_bg);
                silver.setRatingResource(R.drawable.silver_with_bg);
                gold.setRatingResource(R.drawable.star_with_bg);
                platinum.setRatingResource(R.drawable.star_with_bg);
            } else if (data.isHasGold()) {
                Log.d(TAG, "updateAchievement: " + data.getName());
                bronze.setRatingResource(R.drawable.bronze_with_bg);
                silver.setRatingResource(R.drawable.silver_with_bg);
                gold.setRatingResource(R.drawable.gold_with_bg);
                platinum.setRatingResource(R.drawable.star_with_bg);
            } else if (data.isHasPlatinum()) {
                Log.d(TAG, "updateAchievement: " + data.getName());
                bronze.setRatingResource(R.drawable.bronze_with_bg);
                silver.setRatingResource(R.drawable.silver_with_bg);
                gold.setRatingResource(R.drawable.gold_with_bg);
                platinum.setRatingResource(R.drawable.platinam_with_bg);
            } else {
                bronze.setRatingResource(R.drawable.star_with_bg);
                silver.setRatingResource(R.drawable.star_with_bg);
                gold.setRatingResource(R.drawable.star_with_bg);
                platinum.setRatingResource(R.drawable.star_with_bg);
            }
        }
    }
}
