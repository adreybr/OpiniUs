package com.example.android.opinius.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.opinius.R;
import com.example.android.opinius.model.question.Question;

import java.util.List;

public class SurveyAdapter extends ArrayAdapter<Question> {
    List<Question> surveyList;
    Context context;
    int layout;


    public SurveyAdapter(@NonNull Context context, int resource, @NonNull List<Question> questions) {
        super(context, resource, questions);
        this.surveyList = questions;
        this.context = context;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SurveyHolder surveyHolder;

        if (convertView == null) {
            LayoutInflater vi = ((Activity) context).getLayoutInflater();
            convertView = vi.inflate(R.layout.survey_item, parent, false);

            surveyHolder = new SurveyHolder();
            surveyHolder.surveyTitle = convertView.findViewById(R.id.survey_title);
            surveyHolder.surveyNumber = convertView.findViewById(R.id.survey_number);

            convertView.setTag(surveyHolder);
        } else {
            surveyHolder = (SurveyHolder) convertView.getTag();
        }

        Question question = surveyList.get(position);
        String realPos = Integer.toString(position + 1);

        surveyHolder.surveyNumber.setText(realPos);
        surveyHolder.surveyTitle.setText(question.getSurveyTitle());

        return convertView;
    }

    static class SurveyHolder {
        TextView surveyNumber;
        TextView surveyTitle;
    }
}



