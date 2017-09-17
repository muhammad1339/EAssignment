package com.proprog.eassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Doctor;
import model.DoctorModel;
import model.Student;
import model.StudentModel;

public class EditProfileActivity extends AppCompatActivity {

    EditText newName, oldPassword, newPassword;
    Button edit_doctor;
    SharedPreferences doc_sharedPreferences;
    SharedPreferences stu_sharedPreferences;

    Doctor old_doctor, new_doctor;
    Student old_student, new_student;
    DoctorModel doctorModel;
    StudentModel studentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();

        final String user = intent.getStringExtra("user");
        Log.d("ddd",user);
        //name for old doctor & email,id for new doctor
        doc_sharedPreferences = getSharedPreferences("doctor", MODE_PRIVATE);
        final int doc_id = doc_sharedPreferences.getInt("id",1);
        final String doc_name = doc_sharedPreferences.getString("name", "");
        final String doc_email = doc_sharedPreferences.getString("email","");

        stu_sharedPreferences = getSharedPreferences("student", MODE_PRIVATE);
        final int stu_id = stu_sharedPreferences.getInt("id",1);
        final String stu_name = stu_sharedPreferences.getString("name", "");
        final String stu_email = stu_sharedPreferences.getString("email","");


        newName = (EditText) findViewById(R.id.new_name);
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        edit_doctor = (Button) findViewById(R.id.edit_info);





        doctorModel = new DoctorModel(this);
        studentModel = new StudentModel(this);


        edit_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_doctor = new Doctor();
                old_doctor.setName(doc_name);
                old_doctor.setPassword(oldPassword.getText().toString());

                new_doctor = new Doctor();
                new_doctor.setId(doc_id);
                new_doctor.setName(newName.getText().toString());
                new_doctor.setPassword(newPassword.getText().toString());
                new_doctor.setEmail(doc_email);

                old_student = new Student();
                old_student.setName(stu_name);
                old_student.setPassword(oldPassword.getText().toString());
                Log.d("ddd",""+studentModel.student_login(old_student));
                Log.d("ddd",""+old_student.getName());
                Log.d("ddd",""+old_student.getPassword());

                new_student = new Student();
                new_student.setId(stu_id);
                new_student.setName(newName.getText().toString());
                new_student.setPassword(newPassword.getText().toString());
                new_student.setEmail(stu_email);

                if (doctorModel.doctor_login(old_doctor) && user.equals("doctor")) {
                    doctorModel.updateDoctor(new_doctor);
                    doctorModel.doctor_login(new_doctor);
                } else if(studentModel.student_login(old_student) && user.equals("student")){
                    studentModel.updateStudent(new_student);
                    studentModel.student_login(new_student);
                } else{
                    Toast.makeText(EditProfileActivity.this, "Please Try Again....", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
