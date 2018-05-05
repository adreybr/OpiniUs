package com.example.android.opinius.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.model.question.Question;

import java.util.List;

public class ViewFullSurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> questionList;
    private LayoutInflater mInflater;
    private Context context;

    class ShortAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        final TextView questionNumber;
        public final TextView question;
        public final TextView answer;


        ShortAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.short_answer_question_number);
            question = itemView.findViewById(R.id.short_answer_question);
            answer = itemView.findViewById(R.id.answer);
        }
    }

    class SingleAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        final TextView questionNumber;
        public final TextView question;
        public final TextView answer;


        SingleAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.short_answer_question_number);
            question = itemView.findViewById(R.id.short_answer_question);
            answer = itemView.findViewById(R.id.answer);
        }
    }

    class MultipleAnswerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        final TextView questionNumber;
        public final TextView question;
        public final TextView answer;


        MultipleAnswerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            questionNumber = itemView.findViewById(R.id.short_answer_question_number);
            question = itemView.findViewById(R.id.short_answer_question);
            answer = itemView.findViewById(R.id.answer);
        }
    }

    public ViewFullSurveyAdapter(Context context, List<Question> questionList) {
        this.questionList = questionList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return questionList.get(position).getQuestionType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == Question.TYPE_SHORT_ANSWER) {
            View view = layoutInflater.inflate(R.layout.view_question_answer, parent, false);
            return new ShortAnswerViewHolder(view);
        } else if (viewType == Question.TYPE_SINGLE_ANSWER) {
            View view = layoutInflater.inflate(R.layout.view_question_answer, parent, false);
            return new SingleAnswerViewHolder(view);
        } else if (viewType == Question.TYPE_MULTIPLE_ANSWER) {
            View view = layoutInflater.inflate(R.layout.view_question_answer, parent, false);
            return new MultipleAnswerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        String realPos = Integer.toString(position + 1);

        if (holder instanceof ShortAnswerViewHolder) {
            ShortAnswerViewHolder shortAnswerViewHolder = (ShortAnswerViewHolder) holder;

            shortAnswerViewHolder.questionNumber.setText(realPos + ".");
            shortAnswerViewHolder.question.setText(question.getQuestion());
            shortAnswerViewHolder.answer.setText(question.getAnswer());

        } else if (holder instanceof SingleAnswerViewHolder) {
            SingleAnswerViewHolder singleAnswerViewHolder = (SingleAnswerViewHolder) holder;

            singleAnswerViewHolder.questionNumber.setText(realPos + ".");
            singleAnswerViewHolder.question.setText(question.getQuestion());
            singleAnswerViewHolder.answer.setText(question.getAnswer());

        } else if (holder instanceof MultipleAnswerViewHolder) {
            MultipleAnswerViewHolder multipleAnswerViewHolder = (MultipleAnswerViewHolder) holder;

            multipleAnswerViewHolder.questionNumber.setText(realPos + ".");
            multipleAnswerViewHolder.question.setText(question.getQuestion());

            String answer = "";
            String[] answerSplit = question.getAnswer().split("#");

            for (int i = 0; i < answerSplit.length; i++) {
                if (i == answerSplit.length - 1) {
                    answer += answerSplit[i];
                } else {
                    answer += answerSplit[i] + ", ";
                }
            }
            multipleAnswerViewHolder.answer.setText(answer);
        }
    }

    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        } else {
            return 0;
        }
    }

    public void swap(List<Question> questionList) {
        if (questionList == null || questionList.size() == 0)
            return;
        if (this.questionList != null && this.questionList.size() > 0)
            this.questionList.clear();
        this.questionList = questionList;
        notifyDataSetChanged();
    }
}
