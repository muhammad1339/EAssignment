package com.proprog.eassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Doctor;
import model.Student;

/**
 * Created by Muhammad on 23-May-17.
 */

public class StudentAdapter extends BaseAdapter {
    ArrayList<Student> list;
    Context context;
    LayoutInflater inflater;

    public StudentAdapter(Context context,ArrayList<Student> list ) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.course_row_layout, parent, false);

        TextView doctor_name = (TextView) convertView.findViewById(R.id.row_course_name);
        TextView doctor_id = (TextView) convertView.findViewById(R.id.row_course_id);

        doctor_name.setText(list.get(position).getName().toString());
        doctor_id.setText(list.get(position).getId() + "");

        return convertView;
    }
}
