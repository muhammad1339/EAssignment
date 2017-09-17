package com.proprog.eassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Course;

/**
 * Created by Muhammad on 20-May-17.
 */

public class CourseAdapter extends BaseAdapter {
    ArrayList<Course> list;
    Context context;
    LayoutInflater inflater;

    public CourseAdapter(Context context,ArrayList<Course> list ) {
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

        TextView course_name = (TextView) convertView.findViewById(R.id.row_course_name);
        TextView course_id = (TextView) convertView.findViewById(R.id.row_course_id);
        course_name.setText(list.get(position).getName().toString());
        course_id.setText(""+list.get(position).getId());

        return convertView;
    }
}
