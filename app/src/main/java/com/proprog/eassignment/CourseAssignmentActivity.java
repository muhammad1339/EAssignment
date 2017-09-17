package com.proprog.eassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import model.DocAssignment;
import model.DocAssignmentModel;
import model.StudentSolution;
import model.StudentSolutionModel;

public class CourseAssignmentActivity extends AppCompatActivity {
    final static int IMAGE_REQUEST_CODE = 1;
    final static int CAMERA_REQUEST_CODE = 2;
    Uri imageUri;
    boolean got_image = false;
    byte[] imageInByte = null;

    DocAssignment docAssignment, docAssignment2download;
    DocAssignmentModel docAssignmentModel;
    StudentSolution studentSolution;
    StudentSolutionModel studentSolutionModel;

    SharedPreferences stu_sharedPreferences;

    ListView listView;
    TextView desc, inst, start, end;
    Button upload, download;

    int stu_id, assignment_id;
    String stu_name;
    boolean assignment_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_assignment);

        upload = (Button) findViewById(R.id.upload);
        download = (Button) findViewById(R.id.download);

        desc = (TextView) findViewById(R.id.desc);
        inst = (TextView) findViewById(R.id.inst);
        start = (TextView) findViewById(R.id.start);
        end = (TextView) findViewById(R.id.end);


        listView = (ListView) findViewById(R.id.listView);

        final Intent intent = getIntent();
        int course_id = intent.getIntExtra("id", 0);

        stu_sharedPreferences = getSharedPreferences("student", MODE_PRIVATE);
        stu_id = stu_sharedPreferences.getInt("id", 0);
        stu_name = stu_sharedPreferences.getString("name", "");

        docAssignmentModel = new DocAssignmentModel(this);
        studentSolutionModel = new StudentSolutionModel(this);
        studentSolution = new StudentSolution();

        final ArrayList<DocAssignment> docAssignments = new ArrayList<>(docAssignmentModel.getCourse_assignment(course_id));

        listView.setAdapter(new AssignmentAdapter(this, docAssignments));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                assignment_clicked = true;
                TextView textView = (TextView) view.findViewById(R.id.row_course_id);
                assignment_id = Integer.parseInt(textView.getText().toString());
                Log.d("ddd", "aaa " + assignment_id);

                docAssignment = docAssignmentModel.get_single_assignment(assignment_id);

                desc.setText(docAssignment.getDesc());
                inst.setText(docAssignment.getInstruction());
                start.setText(docAssignment.getStart_time());
                end.setText(docAssignment.getEnd_time());
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assignment_clicked) {

                    docAssignment2download = docAssignmentModel.get_single_assignment(assignment_id);
                    byte[] downloaded_image = docAssignment2download.getFile();


                    Intent intent1 = new Intent(CourseAssignmentActivity.this, ViewImageActivity.class);
                    intent1.putExtra("image", downloaded_image);
                    intent1.putExtra("flag", "doctor");
                    startActivity(intent1);


                } else {
                    Toast.makeText(CourseAssignmentActivity.this, "please select assignment first", Toast.LENGTH_LONG).show();

                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assignment_clicked) {
                    getFile();
                } else {
                    Toast.makeText(CourseAssignmentActivity.this, "please select assignment first", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean getFile() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(CourseAssignmentActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Choose Image ");
        dialog.setMessage("from");
        dialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
        return got_image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {

            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(CourseAssignmentActivity.this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageInByte = stream.toByteArray();

                studentSolution.setStu_id(stu_id);
                studentSolution.setAssignment_id(assignment_id);
                studentSolution.setFile(imageInByte);
                studentSolutionModel.add_solution(studentSolution);
                Toast.makeText(CourseAssignmentActivity.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                got_image = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            Bitmap bitmap = (Bitmap) bundle.get("data"); //get for getting object
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageInByte = stream.toByteArray();

            studentSolution.setStu_id(stu_id);
            studentSolution.setAssignment_id(assignment_id);
            studentSolution.setFile(imageInByte);
            studentSolutionModel.add_solution(studentSolution);
            Toast.makeText(CourseAssignmentActivity.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
            got_image = true;

        } else {
            got_image = false;
            Toast.makeText(CourseAssignmentActivity.this, "Assignment isn't uploaded ??!!", Toast.LENGTH_LONG).show();
        }
    }

}
