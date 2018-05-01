package com.example.android.opinius.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.opinius.R;

import java.util.LinkedList;

public class SurveyListAdapter extends
        RecyclerView.Adapter<SurveyListAdapter.SurveyViewHolder> {

    private final LinkedList<String> mSurveyList;
    private LayoutInflater mInflater;

    public SurveyListAdapter(Context context, LinkedList<String> mSurveyList) {
        mInflater = LayoutInflater.from(context);
        this.mSurveyList = mSurveyList;
    }

    @Override
    public int getItemCount() {
        return mSurveyList.size();
    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {

    }

    class SurveyViewHolder extends RecyclerView.ViewHolder {
        public final TextView surveyItemView;
        final SurveyListAdapter mAdapter;

        SurveyViewHolder(View itemView, SurveyListAdapter mAdapter) {
            super(itemView);
            this.surveyItemView = (TextView) itemView.findViewById(R.id.survey);
            this.mAdapter = mAdapter;
        }
    }
}
