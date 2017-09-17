package com.proprog.eassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import model.Course;
import model.CourseModel;

public class CourseActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView course_name,course_id,course_point,course_semester;
    Button update_course,delete_course,student_in_course,assignment;
    Course course;
    CourseModel courseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        course = new Course();
        courseModel = new CourseModel(this);

        sharedPreferences = getSharedPreferences("course",MODE_PRIVATE);
        course.setId(sharedPreferences.getInt("id",1));
        course.setName(sharedPreferences.getString("name",""));


        update_course = (Button) findViewById(R.id.update_course);
        delete_course = (Button) findViewById(R.id.delete_course);
        student_in_course = (Button) findViewById(R.id.student_in_course);
        assignment = (Button) findViewById(R.id.assignment);

        course_id = (TextView) findViewById(R.id.course_id_view);
        course_name = (TextView) findViewById(R.id.course_name_view);
        course_point = (TextView) findViewById(R.id.course_point_view);
        course_semester = (TextView) findViewById(R.id.course_semester_view);


        course_id.setText(sharedPreferences.getInt("id",1)+"");
        course_name.setText(sharedPreferences.getString("name",""));
        course_point.setText(sharedPreferences.getInt("point",1)+"");
        course_semester.setText(sharedPreferences.getString("semester","")+"");

        update_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,EditCourseActivity.class);
                startActivity(intent);
            }
        });
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,AssignmentActivity.class);
                startActivity(intent);
            }
        });
        delete_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseModel.delete_course(course);
                Toast.makeText(CourseActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        student_in_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,StudentInCourseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        course_id.setText(sharedPreferences.getInt("id",1)+"");
        course_name.setText(sharedPreferences.getString("name",""));
        course_point.setText(sharedPreferences.getInt("point",1)+"");
        course_semester.setText(sharedPreferences.getString("semester","")+"");

    }
}
