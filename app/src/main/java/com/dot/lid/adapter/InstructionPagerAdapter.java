package com.dot.lid.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dot.lid.view.instruction.InArabicFragment;
import com.dot.lid.view.instruction.InEnglishFragment;
import com.dot.lid.view.instruction.InGermanFragment;

public class InstructionPagerAdapter extends FragmentPagerAdapter {

    public InstructionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InGermanFragment();
            case 1:
                return new InEnglishFragment();
            case 2:
                return new InArabicFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
