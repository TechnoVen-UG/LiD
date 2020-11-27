package com.dot.lid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dot.lid.R;

public class TestCompleteDialog extends AppCompatDialogFragment {

    public static final String TEST_DIALOG_KEY = "test_dialog";
    private TestDialogListener listener;

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String score = "0/33";
        if (getArguments() != null) {
            score = getArguments().getString(TEST_DIALOG_KEY);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.test_complete_dialog, null);
        builder.setView(view);
        Button yesButton = view.findViewById(R.id.testYesBT);
        Button noButton = view.findViewById(R.id.testNoBT);
        Button shareButton = view.findViewById(R.id.testShareBT);
        Button barChartButton = view.findViewById(R.id.testBarchartBT);
        Button achButton = view.findViewById(R.id.testAchievementBT);
        ImageView cancelButton = view.findViewById(R.id.testCancelIV);
        TextView scoreTV = view.findViewById(R.id.testScoreTV);

        scoreTV.setText(score);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onYes();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNo();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onShare();
            }
        });
        achButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAchievement();
            }
        });
        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBarChart();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();
            }
        });

        builder.setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TestDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface TestDialogListener {
        void onYes();

        void onNo();

        void onShare();

        void onBarChart();

        void onAchievement();

        void onCancel();
    }
}