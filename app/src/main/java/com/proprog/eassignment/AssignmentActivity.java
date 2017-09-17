package com.proprog.eassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.DocAssignment;
import model.DocAssignmentModel;

public class AssignmentActivity extends AppCompatActivity {

    Button upload_assignment;
    ListView list;
    DocAssignmentModel docAssignmentModel;
    DocAssignment docAssignment;
    SharedPreferences doc_sharedPreferences, course_sharedPreferences;
    ArrayList<DocAssignment> list_of_course_assignment;
    int doc_id, course_id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_assignment);
        docAssignmentModel = new DocAssignmentModel(this);
        doc_sharedPreferences = getSharedPreferences("doctor", MODE_PRIVATE);
        doc_id = doc_sharedPreferences.getInt("id", 0);

        course_sharedPreferences = getSharedPreferences("course", MODE_PRIVATE);
        course_id = course_sharedPreferences.getInt("id", 0);

        list = (ListView) findViewById(R.id.listView);

        list_of_course_assignment = new ArrayList<>(docAssignmentModel.getCourse_assignment(course_id));

        list.setAdapter(new AssignmentAdapter(this, list_of_course_assignment));

        upload_assignment = (Button) findViewById(R.id.bt_upload);

        upload_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentActivity.this, AssignmentInfoActivity.class);
                startActivity(intent);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.row_course_id);

                Intent intent = new Intent(AssignmentActivity.this,SolvedAssignmentActivity.class);
                intent.putExtra("id",Integer.parseInt(textView.getText().toString()));
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list_of_course_assignment = new ArrayList<>(docAssignmentModel.getCourse_assignment(course_id));
        list.setAdapter(new AssignmentAdapter(this, list_of_course_assignment));

    }
}
