package com.dot.lid.view.instruction;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dot.lid.R;
import com.dot.lid.adapter.InstructionPagerAdapter;
import com.dot.lid.databinding.ActivityInstructionBinding;

public class InstructionActivity extends AppCompatActivity {

    private static final String TAG = "sayed";

    private ActivityInstructionBinding binding;
    private TextView[] dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InstructionPagerAdapter adapter = new InstructionPagerAdapter(
                getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );
        binding.viewPager.setAdapter(adapter);
        addDot(0);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void addDot(int position) {
        dot = new TextView[3];
        binding.linearLayout.removeAllViews();
        for (int i = 0; i < 3; i++) {
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9679;"));
            dot[i].setTextSize(24);
            dot[i].setTextColor(getResources().getColor(R.color.colorBackground));
            binding.linearLayout.addView(dot[i]);
        }
        dot[position].setTextColor(getResources().getColor(R.color.white));
    }

}
