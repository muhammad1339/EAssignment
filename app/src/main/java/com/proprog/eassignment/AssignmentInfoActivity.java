package com.proprog.eassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import model.DocAssignment;
import model.DocAssignmentModel;

public class AssignmentInfoActivity extends AppCompatActivity {
    final static int IMAGE_REQUEST_CODE = 1;
    final static int CAMERA_REQUEST_CODE = 2;
    Uri imageUri;
    boolean got_image=false;
    byte[] imageInByte=null;

    EditText desc,inst,start,end;
    Button upload,confirm;
    DocAssignment docAssignment;
    DocAssignmentModel docAssignmentModel;

    SharedPreferences doc_sharedPreferences;
    SharedPreferences course_sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_info);

        doc_sharedPreferences = getSharedPreferences("doctor",MODE_PRIVATE);
        final int doc_id = doc_sharedPreferences.getInt("id",1);

        course_sharedPreferences = getSharedPreferences("course",MODE_PRIVATE);
        final int course_id = course_sharedPreferences.getInt("id",1);


        desc = (EditText) findViewById(R.id.desc);
        inst = (EditText) findViewById(R.id.inst);
        start = (EditText) findViewById(R.id.start);
        end = (EditText) findViewById(R.id.end);

        upload = (Button) findViewById(R.id.bt_upload);
        confirm = (Button) findViewById(R.id.bt_submit);
        docAssignment = new DocAssignment();
        docAssignmentModel = new DocAssignmentModel(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(got_image) {
                docAssignment.setDesc(desc.getText().toString());
                docAssignment.setInstruction(inst.getText().toString());
                docAssignment.setStart_time(start.getText().toString());
                docAssignment.setEnd_time(end.getText().toString());
                docAssignment.setDoc_id(doc_id);
                docAssignment.setCourse_id(course_id);
                docAssignment.setFile(imageInByte);
                    if(docAssignmentModel.check_duration(docAssignment)){
                        docAssignmentModel.add_doc_assignment(docAssignment);
                    }else{
                        Toast.makeText(AssignmentInfoActivity.this, "please check start date and end time", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AssignmentInfoActivity.this, "please check if assignment information is correct", Toast.LENGTH_SHORT).show();
                }

            }
        });







    }
    public boolean getFile(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(AssignmentInfoActivity.this);
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(AssignmentInfoActivity.this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageInByte = stream.toByteArray();
                got_image=true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            Bitmap bitmap = (Bitmap) bundle.get("data"); //get for getting object
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageInByte = stream.toByteArray();
            got_image = true;

        } else {
            got_image = false;
            Toast.makeText(AssignmentInfoActivity.this, "Assignment isn't uploaded ??!!", Toast.LENGTH_LONG).show();
        }
    }

}
