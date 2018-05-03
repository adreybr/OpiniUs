package com.example.android.opinius.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.database.model.question.Question;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.QuestionViewHolder> {

    private final List<Question> questions;
    private LayoutInflater mInflater;

    public RVAdapter(Context context, List<Question> questions) {
        this.questions = questions;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RVAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.short_anwer_item,
                parent, false);
        QuestionViewHolder rvh = new QuestionViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.question.setText(questions.get(position).getQuestion());

    }

    @Override
    public int getItemCount() {
        if (questions != null) {
            return questions.size();
        } else {
            return 0;
        }
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public final TextView question;
//        public final EditText answer;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            question = itemView.findViewById(R.id.single_answer_question);
//            answer = itemView.findViewById(R.id.question_answer);

        }
    }


}
