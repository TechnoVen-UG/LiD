package com.dot.lid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dot.lid.R;
import com.dot.lid.model.Selection;

import java.util.List;

public class ShowAnswerAdapter extends RecyclerView.Adapter<ShowAnswerAdapter.ShowAnswerViewHolder> {
    private static final String TAG = "sayed";
    private List<Selection> selectionList;

    public ShowAnswerAdapter(List<Selection> selectionList) {
        this.selectionList = selectionList;
    }

    @NonNull
    @Override
    public ShowAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_answer_item, parent, false);
        return new ShowAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAnswerViewHolder holder, int position) {

        try {
            Selection selection = selectionList.get(position);
            //Log.d(TAG, "onBindViewHolder: selection"+selection.getSelectionNumber()+"  correct = "+selection.getQuestion().getCorrectAnswer());
            holder.question.setText(selection.getQuestion().getQuestion());
            if (selection.getSelectionNumber() == selection.getQuestion().getCorrectAnswer()) {
                holder.wornAnsIV.setVisibility(View.GONE);
                holder.wrongAns.setVisibility(View.GONE);
                holder.correctAns.setText(getCorrectAnswer(selection));
            } else {
                holder.wornAnsIV.setVisibility(View.VISIBLE);
                holder.wrongAns.setVisibility(View.VISIBLE);
                holder.correctAns.setText(getCorrectAnswer(selection));
                holder.wrongAns.setText(getSelectionAnswer(selection));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        if (selectionList.isEmpty()) {
            return 0;
        } else {
            return selectionList.size();
        }
    }

    private String getSelectionAnswer(Selection selection) {
        switch (selection.getSelectionNumber()) {
            case 1:
                return selection.getQuestion().getAnswer1();
            case 2:
                return selection.getQuestion().getAnswer2();
            case 3:
                return selection.getQuestion().getAnswer3();
            case 4:
                return selection.getQuestion().getAnswer4();
            default:
                return "";
        }
    }

    private String getCorrectAnswer(Selection selection) {
        switch (selection.getQuestion().getCorrectAnswer()) {
            case 1:
                return selection.getQuestion().getAnswer1();
            case 2:
                return selection.getQuestion().getAnswer2();
            case 3:
                return selection.getQuestion().getAnswer3();
            case 4:
                return selection.getQuestion().getAnswer4();
            default:
                return "";
        }
    }

    static class ShowAnswerViewHolder extends RecyclerView.ViewHolder {

        private TextView question;
        private TextView correctAns;
        private TextView wrongAns;
        private ImageView wornAnsIV;

        public ShowAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.ansQuestionTV);
            correctAns = itemView.findViewById(R.id.ansCorrectTV);
            wrongAns = itemView.findViewById(R.id.ansWrongTV);
            wornAnsIV = itemView.findViewById(R.id.ansWrongIV);
        }
    }
}
