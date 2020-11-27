package com.dot.lid.view.instruction;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dot.lid.R;
import com.dot.lid.app.MyApplication;
import com.dot.lid.databinding.LayoutInstructionItemBinding;
import com.dot.lid.view.main.MainActivity;

import java.util.Locale;

import static com.dot.lid.utils.Language.ARABIC;

public class InArabicFragment extends Fragment {
    private LayoutInstructionItemBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutInstructionItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Locale locale = new Locale(ARABIC.getLanguage());
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);

        binding.instruction.setText(R.string.instructions);
        binding.instructionDetails.setText(R.string.instruction_details);
        binding.goToAppBT.setText(R.string.goto_to_app);

        binding.goToAppBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstance().setLanguage(ARABIC.getLanguage());
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
            }
        });
    }
}
