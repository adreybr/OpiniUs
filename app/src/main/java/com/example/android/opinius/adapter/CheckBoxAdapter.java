package com.example.android.opinius.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.android.opinius.R;

import java.util.List;

public class CheckBoxAdapter extends ArrayAdapter<String> {

    List<String> choicesList;
    Context context;
    int layout;

    public CheckBoxAdapter(@NonNull Context context, int layout, @NonNull List<String> choicesList) {
        super(context, layout, choicesList);
        this.choicesList = choicesList;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        OptionHolder holder;

        if (convertView == null) {
            LayoutInflater vi = ((Activity) context).getLayoutInflater();
            convertView = vi.inflate(R.layout.checkbox_item, parent, false);

            holder = new OptionHolder();
            holder.checkBoxView = convertView.findViewById(R.id.checkbox_option);

            convertView.setTag(holder);
        } else {
            holder = (OptionHolder) convertView.getTag();
        }

        String choice = choicesList.get(position);
        holder.checkBoxView.setText(choice);

        return convertView;
    }

    static class OptionHolder {
        CheckBox checkBoxView;
    }
}
