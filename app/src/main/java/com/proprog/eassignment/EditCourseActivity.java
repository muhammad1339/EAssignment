package com.proprog.eassignment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import model.Course;
import model.CourseModel;

public class EditCourseActivity extends AppCompatActivity {
    EditText course_name, course_point;
    TextView course_id;
    RadioGroup radioGroup;
    RadioButton first, second;

    SharedPreferences sharedPreferences;
    SharedPreferences doc_sharedPreferences;

    Button submit, clear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        radioGroup = (RadioGroup) findViewById(R.id.add_group);
        first = (RadioButton) findViewById(R.id.first);
        second = (RadioButton) findViewById(R.id.second);

        sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
        doc_sharedPreferences = getSharedPreferences("doctor", MODE_PRIVATE);
        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);

        course_id = (TextView) findViewById(R.id.course_id);

        course_name = (EditText) findViewById(R.id.course_name);
        course_point = (EditText) findViewById(R.id.course_point);


        course_id.setText(sharedPreferences.getInt("id", 1) + "");
        course_name.setText(sharedPreferences.getString("name", ""));
        course_point.setText(sharedPreferences.getInt("point", 1) + "");
        String semester = sharedPreferences.getString("semester", "");
        if (semester.equals("1st")) {
            radioGroup.check(R.id.first);
        } else {
            radioGroup.check(R.id.second);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course course = new Course();
                CourseModel courseModel = new CourseModel(EditCourseActivity.this);

                String id = course_id.getText().toString();
                String name = course_name.getText().toString();
                String point = course_point.getText().toString();
                String semester = "";
                if (radioGroup.getCheckedRadioButtonId() == R.id.first) {
                    semester = first.getText().toString();
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.second) {
                    semester = second.getText().toString();
                } else {
                    Toast.makeText(EditCourseActivity.this, "please select course's semester", Toast.LENGTH_SHORT).show();
                }
                if (semester.equals("")) {
                    Toast.makeText(EditCourseActivity.this, "please select course's semester", Toast.LENGTH_SHORT).show();
                } else {
                    course.setId(Integer.parseInt(id));
                    course.setName(name);
                    course.setPoint(Integer.parseInt(point));
                    course.setSemester(semester);
                    course.setDoc_id(doc_sharedPreferences.getInt("id",1));

                    SharedPreferences sharedPreferences = EditCourseActivity.this.getSharedPreferences("course", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", course.getName());
                    editor.putInt("id", course.getId());
                    editor.putInt("point", course.getPoint());
                    editor.putString("semester", course.getSemester());
                    editor.putInt("doc_id", course.getDoc_id());
                    editor.commit();
                    Log.d("ddd",""+course.getId());

                    courseModel.update_course(course);
                    Toast.makeText(EditCourseActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course_id.setText("");
                course_name.setText("");
                course_point.setText("");
                radioGroup.clearCheck();
            }
        });




    }

}
