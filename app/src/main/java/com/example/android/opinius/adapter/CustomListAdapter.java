package com.example.android.opinius.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.database.model.question.Question;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Question> {

    List<Question> questionList;
    Context context;
    int layout;


    public CustomListAdapter(@NonNull Context context, int layout, @NonNull List<Question> questionList) {
        super(context, layout, questionList);
        this.context = context;
        this.layout = layout;
        this.questionList = questionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        QuestionHolder holder;

        if (v == null) {
            LayoutInflater vi = ((Activity) context).getLayoutInflater();
            v = vi.inflate(layout, parent, false);

            holder = new QuestionHolder();
            holder.optionView = v.findViewById(R.id.checkbox_image);
            holder.optionView = v.findViewById(R.id.checkbox_value);

            v.setTag(holder);
        } else {
            holder = (QuestionHolder) v.getTag();
        }

        Question question = questionList.get(position);
        holder.optionView.setText(question.getQuestion());

        return v;
    }

    static class QuestionHolder {
        ImageView imageView;
        EditText optionView;
    }

}
