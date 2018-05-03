package com.example.android.opinius.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.database.model.question.Question;

import java.util.List;

public class QuestionListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Question> questionList;
    private LayoutInflater mInflater;

    class ShortAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public final TextView questionNumber;
        public final TextView question;


        public ShortAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.short_answer_question_number);
            question = itemView.findViewById(R.id.short_answer_question);
        }
    }

    class SingleAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public final TextView questionNumber;
        public final TextView question;
        public final RadioGroup radioGroup;

        public SingleAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.single_answer_question_number);
            question = itemView.findViewById(R.id.single_answer_question);
            radioGroup = itemView.findViewById(R.id.single_answer_radiogroup);
        }
    }

    class MultipleAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public final TextView questionNumber;
        public final TextView question;
        public final ListView listView;

        public MultipleAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.multi_answer_question_number);
            question = itemView.findViewById(R.id.multi_answer_question);
            listView = itemView.findViewById(R.id.listview_multiple);
        }
    }

    public QuestionListRecyclerAdapter(Context context, List<Question> questionList) {
        this.questionList = questionList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
