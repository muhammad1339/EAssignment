package com.proprog.eassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Course;
import model.Student;
import model.StudentInCourseModel;

public class StudentActivity extends AppCompatActivity {
    String info;
    Student student;
    SharedPreferences sharedPreferences;
    Button enroll;
    ListView listView;
    StudentInCourseModel studentInCourseModel;
    ArrayList<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        sharedPreferences = getSharedPreferences("student", MODE_PRIVATE);
        student = new Student();
        fill_student_data();

        studentInCourseModel = new StudentInCourseModel(this);
        courses = new ArrayList<>(studentInCourseModel.getEnrolled(student.getId()));

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new CourseAdapter(this,courses));

        enroll = (Button) findViewById(R.id.enroll);



        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this,EnrollActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.row_course_id);
                Intent intent = new Intent(StudentActivity.this,CourseAssignmentActivity.class);
                intent.putExtra("id",Integer.parseInt(textView.getText().toString()));
                startActivity(intent);

            }
        });

    }

    public void view_student_data(View view) {
        info = "ID: \n" + student.getId() + "\n"
                + "Name: \n" + student.getName() + "\n"
                + "E-mail: \n" + student.getEmail() + "\n"
                + "password: \n" + student.getPassword() + "\n";

        AlertDialog.Builder dialog = new AlertDialog.Builder(StudentActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Your Profile");
        dialog.setMessage(info);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(StudentActivity.this, EditProfileActivity.class);
                intent.putExtra("user", "student");
                startActivity(intent);
            }
        }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();

    }

    public void fill_student_data() {
        int id = sharedPreferences.getInt("id", 0);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        String email = sharedPreferences.getString("email", "");
        student.setId(id);
        student.setName(name);
        student.setPassword(password);
        student.setEmail(email);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fill_student_data();
        courses.clear();
        courses = new ArrayList<>(studentInCourseModel.getEnrolled(student.getId()));
        listView.setAdapter(new CourseAdapter(StudentActivity.this,courses));

    }
}
