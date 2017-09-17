package com.proprog.eassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import model.DocAssignment;
import model.DocAssignmentModel;
import model.Student;
import model.StudentInCourseModel;
import model.StudentSolution;
import model.StudentSolutionModel;

public class SolvedAssignmentActivity extends AppCompatActivity {
    ImageView assignment_content;
    ListView listView;
    StudentSolutionModel studentSolutionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_assignment);
        final Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        Log.d("ddd",id+"");

        listView = (ListView) findViewById(R.id.listView);
        ArrayList<Student> students ;

        studentSolutionModel = new StudentSolutionModel(this);
        assignment_content = (ImageView) findViewById(R.id.assignment_content);
        students = studentSolutionModel.get_student_solved(id);

        listView.setAdapter(new StudentAdapter(this,students));

        DocAssignment docAssignment = new DocAssignmentModel(this).get_single_assignment(id);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(docAssignment.getFile());
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        assignment_content.setImageBitmap(bitmap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(SolvedAssignmentActivity.this,ViewImageActivity.class);
                TextView textView = (TextView) findViewById(R.id.row_course_id);
                int stu_id = Integer.parseInt(textView.getText().toString());
                intent1.putExtra("flag","student");
                intent1.putExtra("image",studentSolutionModel.get_student_solution(stu_id));
                startActivity(intent1);
            }
        });



    }
}
