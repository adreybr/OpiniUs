package com.example.android.opinius.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.database.model.question.Question;

import java.util.Arrays;
import java.util.List;

public class QuestionListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> questionList;
    private LayoutInflater mInflater;
    private Context context;

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
            View view = layoutInflater.inflate(R.layout.short_anwer_item, parent, false);
            return new ShortAnswerViewHolder(view);
        } else if (viewType == Question.TYPE_SINGLE_ANSWER) {
            View view = layoutInflater.inflate(R.layout.single_answer_item, parent, false);
            return new SingleAnswerViewHolder(view);
        } else if (viewType == Question.TYPE_MULTIPLE_ANSWER) {
            View view = layoutInflater.inflate(R.layout.multiple_answer_item, parent, false);
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

        } else if (holder instanceof SingleAnswerViewHolder) {
            SingleAnswerViewHolder singleAnswerViewHolder = (SingleAnswerViewHolder) holder;

            singleAnswerViewHolder.questionNumber.setText(realPos + ".");
            singleAnswerViewHolder.question.setText(question.getQuestion());

            String[] answerList = question.getAnswerList().split("#");
            for (int i = 0; i < answerList.length; i++) {
                Log.d("ADREY GANTENG", "onBindViewHolder: answerlistSINGLE " + answerList[i]);
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(answerList[i]);
                radioButton.setId(i + 1);
                singleAnswerViewHolder.radioGroup.addView(radioButton);
            }

        } else if (holder instanceof MultipleAnswerViewHolder) {
            MultipleAnswerViewHolder multipleAnswerViewHolder = (MultipleAnswerViewHolder) holder;

            multipleAnswerViewHolder.questionNumber.setText(realPos + ".");
            multipleAnswerViewHolder.question.setText(question.getQuestion());

            String[] answerSplit = question.getAnswerList().split("#");


            List<String> answerList = Arrays.asList(answerSplit);

            for (String dodo : answerList
                    ) {
                Log.d("DODO", "onBindViewHolder: " + dodo);

            }

            CheckBoxAdapter dataAdapter = new CheckBoxAdapter(context,
                    R.layout.checkbox_item, answerList);
            multipleAnswerViewHolder.listView.setAdapter(dataAdapter);
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
