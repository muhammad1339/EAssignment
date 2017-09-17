package com.proprog.eassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import model.Course;
import model.CourseModel;

public class AddCourseActivity extends AppCompatActivity {

    EditText id, name, point;
    Button submit, clear;
    RadioGroup radioGroup;
    RadioButton first, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        id = (EditText) findViewById(R.id.course_id);
        name = (EditText) findViewById(R.id.course_name);
        point = (EditText) findViewById(R.id.course_point);

        radioGroup = (RadioGroup) findViewById(R.id.add_group);
        first = (RadioButton) findViewById(R.id.first);
        second = (RadioButton) findViewById(R.id.second);

        submit = (Button) findViewById(R.id.submit_course);
        clear = (Button) findViewById(R.id.clear_course);


        SharedPreferences sharedPreferences = getSharedPreferences("doctor", MODE_PRIVATE);
        final int doc_id = sharedPreferences.getInt("id", 0);

        final CourseModel courseModel = new CourseModel(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_fields()) {
                    int course_id = Integer.parseInt(id.getText().toString());
                    String course_name = name.getText().toString();
                    int course_point = Integer.parseInt(point.getText().toString());
                    String semester = "";
                    if(radioGroup.getCheckedRadioButtonId() == R.id.first){
                        semester = first.getText().toString();
                    } else if(radioGroup.getCheckedRadioButtonId() == R.id.second){
                        semester = second.getText().toString();
                    } else {
                        Toast.makeText(AddCourseActivity.this, "please select course's semester", Toast.LENGTH_SHORT).show();
                    }

                    try {
                        if(semester.equals("")){
                            Toast.makeText(AddCourseActivity.this, "please select course's semester", Toast.LENGTH_SHORT).show();
                        } else{
                            Course course = new Course();
                            course.setId(course_id);
                            course.setName(course_name);
                            course.setPoint(course_point);
                            course.setSemester(semester);
                            course.setDoc_id(doc_id);
                            courseModel.addCourse(course);
                        }
                    }catch (Exception ex){
                        Toast.makeText(AddCourseActivity.this, "it's already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCourseActivity.this, "please complete your course information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                name.setText("");
                point.setText("");
                radioGroup.clearCheck();
            }
        });
    }

    public boolean check_fields() {
        String course_id = id.getText().toString();
        String course_name = name.getText().toString();
        String course_point = point.getText().toString();
        if (course_id.equals("") || course_name.equals("") || course_point.equals("") || radioGroup.isSelected()) {
            return false;
        } else {
            return true;
        }
    }
}
