package com.proprog.eassignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import model.Doctor;
import model.Student;

public class LoginActivity extends AppCompatActivity {

    public final String login_name = "admin";

    EditText name, password;
    Button button;

    RadioGroup radioGroup;
    RadioButton stu, doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        name = (EditText) findViewById(R.id.login_name);
        password = (EditText) findViewById(R.id.login_password);

        radioGroup = (RadioGroup) findViewById(R.id.login_group);
        stu = (RadioButton) findViewById(R.id.stu_rb);
        doc = (RadioButton) findViewById(R.id.doc_rb);

        button = (Button) findViewById(R.id.Log_bt);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals(login_name) && password.getText().toString().equals(login_name)&&!(stu.isChecked()||doc.isChecked())) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                } else {
                    if (!name.getText().toString().equals(login_name) && radioGroup.getCheckedRadioButtonId() == R.id.stu_rb) {
                        stu.setTextColor(Color.GREEN);
                        doc.setTextColor(Color.RED);
                        if (!name.getText().equals("") && !password.getText().equals("")) {
                            Student student = new Student(name.getText().toString(), password.getText().toString());
                            if (new model.StudentModel(LoginActivity.this).student_login(student)) {
                                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "please enter a valid username & password", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!name.getText().toString().equals(login_name) && radioGroup.getCheckedRadioButtonId() == R.id.doc_rb) {
                        doc.setTextColor(Color.GREEN);
                        stu.setTextColor(Color.RED);
                        if (!name.getText().equals("") && !password.getText().equals("")) {
                            Doctor doctor = new Doctor(name.getText().toString(), password.getText().toString());
                            if (new model.DoctorModel(LoginActivity.this).doctor_login(doctor)) {
                                Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "wrong username or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "please enter a valid username & password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "please enter a valid username && password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
