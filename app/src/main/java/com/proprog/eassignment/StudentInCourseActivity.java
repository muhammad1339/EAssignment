package com.proprog.eassignment;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import model.Student;
import model.StudentInCourse;
import model.StudentInCourseModel;

public class StudentInCourseActivity extends AppCompatActivity {

    ListView listView ;
    SharedPreferences sharedPreferences;
    StudentInCourseModel studentInCourseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_in_course);

        studentInCourseModel = new StudentInCourseModel(this);

        sharedPreferences = getSharedPreferences("course",MODE_PRIVATE);
        int course_id = sharedPreferences.getInt("id",1);

        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Student> list = new ArrayList<>(studentInCourseModel.get_student_in_course(course_id));
        Log.d("eee","S : "+course_id);

        listView.setAdapter(new StudentAdapter(this,list));

    }
}
