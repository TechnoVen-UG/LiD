package com.dot.lid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dot.lid.R;

public class InstructionDialog extends AppCompatDialogFragment {

    private InstructionDialogInterface listener;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.layout_instruction_dialog, null);
        ImageView cancelButton = view.findViewById(R.id.instructionDialogCancelBT);
        TextView title = view.findViewById(R.id.instructionDialogTitleTV);
        TextView description = view.findViewById(R.id.instructionDialogDetailsTV);
        title.setText(R.string.instructions);
        description.setText(R.string.instruction_details);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInstructionDialogCancel();
            }
        });

        builder.setView(view);
        builder.setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (InstructionDialog.InstructionDialogInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface InstructionDialogInterface {
        void onInstructionDialogCancel();
    }
}
