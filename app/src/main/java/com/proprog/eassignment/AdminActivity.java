package com.proprog.eassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton doc, stu;

    Button add, update, delete,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        radioGroup = (RadioGroup) findViewById(R.id.add_group);
        stu = (RadioButton) findViewById(R.id.stu);
        doc = (RadioButton) findViewById(R.id.doc);

        add = (Button) findViewById(R.id.add);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        view = (Button) findViewById(R.id.view);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == stu.getId()) {
                    setActionForUser("student","add");
                } else if (radioGroup.getCheckedRadioButtonId() == doc.getId()) {
                    setActionForUser("doctor","add");
                } else {
                    Toast.makeText(AdminActivity.this, "please select user to do action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == stu.getId()) {
                    setActionForUser("student","update");
                } else if (radioGroup.getCheckedRadioButtonId() == doc.getId()) {
                    setActionForUser("doctor","update");
                } else {
                    Toast.makeText(AdminActivity.this, "please select user to do action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,UserViewActivity.class);
                if (radioGroup.getCheckedRadioButtonId() == stu.getId()) {
                    intent.putExtra("user","student");
                    startActivity(intent);
                } else if (radioGroup.getCheckedRadioButtonId() == doc.getId()) {
                    intent.putExtra("user","doctor");
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminActivity.this, "please select user to do action", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setActionForUser(String user_name,String action){
        Intent intent = new Intent(AdminActivity.this,AddStudent_Doctor.class);
        intent.putExtra("user",user_name);
        intent.putExtra("action",action);
        startActivity(intent);
    }
}
