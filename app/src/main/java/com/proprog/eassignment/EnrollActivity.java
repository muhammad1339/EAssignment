package com.proprog.eassignment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Course;
import model.CourseModel;
import model.Doctor;
import model.DoctorModel;
import model.StudentInCourse;
import model.StudentInCourseModel;

public class EnrollActivity extends AppCompatActivity {

    ListView doc_list,course_list;
    DoctorModel doctorModel;
    CourseModel courseModel;
    StudentInCourseModel studentInCourseModel;
    StudentInCourse studentInCourse;
    Doctor doctor;
    SharedPreferences stu_sharedPreferences;
    int stu_id,course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        stu_sharedPreferences = getSharedPreferences("student",MODE_PRIVATE);
        stu_id = stu_sharedPreferences.getInt("id",1);

        studentInCourseModel = new StudentInCourseModel(this);

        doctorModel = new DoctorModel(this);
        doctor = new Doctor();
        courseModel = new CourseModel(this);



        doc_list = (ListView) findViewById(R.id.doctor_list);
        course_list = (ListView) findViewById(R.id.course_list);

        ArrayList<Doctor> list_of_doctor = doctorModel.getAllDoctors();

        for (Doctor doctor: list_of_doctor){
            Log.d("ddd",doctor.getName());
        }

        doc_list.setAdapter(new DoctorAdapter(this,list_of_doctor));

        doc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.row_course_id);
                int doc_id = Integer.parseInt(textView.getText().toString());
                doctor.setId(doc_id);
                ArrayList<Course> courses = courseModel.getDoctor_course(doctor);
                course_list.setAdapter(new CourseAdapter(EnrollActivity.this,courses));
                course_list.deferNotifyDataSetChanged();

            }
        });

        course_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.row_course_id);

                course_id = Integer.parseInt(textView.getText().toString());
                studentInCourse = new StudentInCourse();

                AlertDialog.Builder dialog = new AlertDialog.Builder(EnrollActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("you can subscribe in course");
                dialog.setMessage("choose it or Give Up");
                dialog.setPositiveButton("Choose It", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // get student id and course id
                        try {

                            studentInCourse.setCourse_id(course_id);
                            studentInCourse.setStudent_id(stu_id);

                            studentInCourseModel.add_s_in_c(studentInCourse);

                        }catch (Exception ex){
                            Toast.makeText(EnrollActivity.this, "You already in this course", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                        .setNegativeButton("Give Up", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();

            }
        });

    }
}
