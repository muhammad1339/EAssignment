package com.proprog.eassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.Course;
import model.CourseModel;
import model.Doctor;

public class DoctorActivity extends AppCompatActivity {

    ListView course_list_view;
    Button add_course;
    SharedPreferences sharedPreferences;
    ArrayList<Course> list_of_course;
    Doctor doctor;
    CourseModel courseModel;
    String info;
    TextView course_name, course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        courseModel = new CourseModel(this);
        doctor = new Doctor();
        sharedPreferences = getSharedPreferences("doctor", MODE_PRIVATE);

        fill_doctor_data();

        list_of_course = new ArrayList<>(courseModel.getDoctor_course(doctor));


        course_list_view = (ListView) findViewById(R.id.doctor_course_list);
        add_course = (Button) findViewById(R.id.add_course);

        course_list_view.setAdapter(new CourseAdapter(this, list_of_course));
//        course_list_view.deferNotifyDataSetChanged();
        course_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                course_id = (TextView) view.findViewById(R.id.row_course_id);
                int id_key = Integer.parseInt(course_id.getText().toString());

                Course course = new CourseModel(DoctorActivity.this).get_single_course(id_key);

                Intent intent = new Intent(DoctorActivity.this, CourseActivity.class);

                SharedPreferences sharedPreferences = DoctorActivity.this.getSharedPreferences("course", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", course.getName());
                editor.putInt("id", course.getId());
                editor.putInt("point", course.getPoint());
                editor.putString("semester", course.getSemester());
                editor.putInt("doc_id",course.getDoc_id());
                editor.commit();

                startActivity(intent);
            }
        });
        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });


    }

    public void view_doctor_data(View view) {


        info = "ID: \n" + doctor.getId() + "\n"
                + "Name: \n" + doctor.getName() + "\n"
                + "E-mail: \n" + doctor.getEmail() + "\n"
                + "password: \n" + doctor.getPassword() + "\n";

        AlertDialog.Builder dialog = new AlertDialog.Builder(DoctorActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Your Profile");
        dialog.setMessage(info);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(DoctorActivity.this, EditProfileActivity.class);
                intent.putExtra("user", "doctor");
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

    @Override
    protected void onRestart() {
        super.onRestart();
        fill_doctor_data();
        list_of_course.clear();
        list_of_course = courseModel.getDoctor_course(doctor);
        course_list_view.setAdapter(new CourseAdapter(this, list_of_course));

    }

    public void fill_doctor_data() {

        int id = sharedPreferences.getInt("id", 1);
        String name = sharedPreferences.getString("name", "Not Defined");
        String password = sharedPreferences.getString("password", "Not Defined");
        String email = sharedPreferences.getString("email", "Not Defined");

        doctor.setId(id);
        doctor.setName(name);
        doctor.setEmail(email);
        doctor.setPassword(password);
    }
}
