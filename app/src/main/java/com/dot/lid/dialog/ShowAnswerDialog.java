package com.dot.lid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dot.lid.R;
import com.dot.lid.adapter.ShowAnswerAdapter;
import com.dot.lid.model.Selection;

import java.util.List;

public class ShowAnswerDialog extends AppCompatDialogFragment {

    private ShowAnswerDialog.ShowAnswerInterface listener;
    private List<Selection> selectionList;

    public ShowAnswerDialog(List<Selection> selectionList) {
        this.selectionList = selectionList;
    }

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
        setHasOptionsMenu(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.show_answer_dialog, null);
        ImageView cancelButton = view.findViewById(R.id.ansCancelIV);
        RecyclerView recyclerView = view.findViewById(R.id.ansRec);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        ShowAnswerAdapter answerAdapter = new ShowAnswerAdapter(selectionList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(answerAdapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAnswerDialogCancel();
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
            listener = (ShowAnswerInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ShowAnswerInterface {
        void onAnswerDialogCancel();
    }
}
