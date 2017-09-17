package com.proprog.eassignment;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Doctor;
import model.DoctorModel;
import model.Student;
import model.StudentModel;

public class AddStudent_Doctor extends AppCompatActivity {
    EditText id, name, email, password;
    Button submit, clear;
    String user_name, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_doctor);


        id = (EditText) findViewById(R.id.user_id);
        name = (EditText) findViewById(R.id.user_name);
        email = (EditText) findViewById(R.id.user_eamil);
        password = (EditText) findViewById(R.id.user_password);

        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user");
        action = intent.getStringExtra("action");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (user_name.equals("student") && action.equals("add")) {       //add student
                        Student student = get_student_data();
                        StudentModel studentModel = new StudentModel(AddStudent_Doctor.this);
                        studentModel.addStudent(student);
                    } else if (user_name.equals("doctor") && action.equals("add")) { //add doctor
                        DoctorModel doctorModel = new DoctorModel(AddStudent_Doctor.this);
                        Doctor doctor = get_doctor_data();
                        doctorModel.addDoctor(doctor);
                    } else if (user_name.equals("student") && action.equals("update")) { //update student
                        Student student = get_student_data();
                        StudentModel studentModel = new StudentModel(AddStudent_Doctor.this);
                        studentModel.updateStudent(student);
                    } else if (user_name.equals("doctor") && action.equals("update")) { //update doctor
                        DoctorModel doctorModel = new DoctorModel(AddStudent_Doctor.this);
                        Doctor doctor = get_doctor_data();
                        doctorModel.updateDoctor(doctor);
                    }
                } catch (Exception ex) {
                    Toast.makeText(AddStudent_Doctor.this, "something went wrong.\nplease, try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                name.setText("");
                email.setText("");
                password.setText("");
            }
        });

    }

    public Student get_student_data() {
        Student student = new Student();
        student.setId(Integer.parseInt(id.getText().toString()));
        student.setName(name.getText().toString());
        student.setEmail(email.getText().toString());
        student.setPassword(password.getText().toString());
        return student;
    }

    public Doctor get_doctor_data() {
        Doctor doctor = new Doctor();
        doctor.setId(Integer.parseInt(id.getText().toString()));
        doctor.setName(name.getText().toString());
        doctor.setEmail(email.getText().toString());
        doctor.setPassword(password.getText().toString());
        return doctor;
    }


}
