package com.proprog.eassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import model.Doctor;
import model.DoctorModel;
import model.Student;
import model.StudentInCourseModel;
import model.StudentModel;

public class UserViewActivity extends AppCompatActivity {

    ListView listView;
    StudentModel studentModel;
    DoctorModel doctorModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        studentModel = new StudentModel(this);
        doctorModel = new DoctorModel(this);

        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        if (user.equals("student")) {
            ArrayList<Student> students = new ArrayList<>(studentModel.getAllStudent());
            listView.setAdapter(new StudentAdapter(this,students));

        } else if (user.equals("doctor")) {
            ArrayList<Doctor> doctors = new ArrayList<>(doctorModel.getAllDoctors());
            listView.setAdapter(new DoctorAdapter(this,doctors));
        }

    }
}
