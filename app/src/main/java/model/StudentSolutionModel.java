package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Muhammad on 23-May-17.
 */

public class StudentSolutionModel {
    DataHelper dataHelper;
    SQLiteDatabase sql;
    Context context;

    public StudentSolutionModel(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
        sql = dataHelper.getWritableDatabase();
    }

    public void add_solution(StudentSolution studentSolution) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataHelper.COL_SS_ASSIGN_ID, studentSolution.getAssignment_id());
            contentValues.put(DataHelper.COL_SS_STU_ID, studentSolution.getStu_id());
            contentValues.put(DataHelper.COL_SS_FILE, studentSolution.getFile());

            sql.insertOrThrow(DataHelper.TABLE_STUDENT_SOLUTION, null, contentValues);
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException ex) {
            Toast.makeText(context, "Already added.\nplease, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<StudentSolution> get_solution(int assignment_id) {
        StudentSolution studentSolution;
        ArrayList<StudentSolution> studentSolutions = new ArrayList<>();
        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT_SOLUTION,
                new String[]{DataHelper.COL_SS_ASSIGN_ID,DataHelper.COL_SS_STU_ID
        ,DataHelper.COL_SS_FILE},
                DataHelper.COL_SS_ASSIGN_ID + "=" + assignment_id,
                null, null, null, null);
        while (cursor.moveToNext()) {
            studentSolution = new StudentSolution();
            studentSolution.setAssignment_id(cursor.getInt(0));
            studentSolution.setStu_id(cursor.getInt(1));
            studentSolution.setFile(cursor.getBlob(2));
            studentSolutions.add(studentSolution);
        }
        return studentSolutions;
    }

    public ArrayList<Student> get_student_solved(int assignment_id) {

        ArrayList<StudentSolution> studentSolutions = get_solution(assignment_id);
        ArrayList<Student> students = new ArrayList<>();
        Student student;

        for (StudentSolution studentSolution : studentSolutions) {
            Cursor cursor = sql.query(DataHelper.TABLE_STUDENT,
                    new String[]{DataHelper.COL_STU_NAME, DataHelper.COL_STU_PASSWORD,
                            DataHelper.COL_STU_ID, DataHelper.COL_STU_EMAIL},
                    DataHelper.COL_STU_ID + "=" + studentSolution.getStu_id(),
                    null, null, null, null);
            if (cursor.moveToFirst()) {
                student = new Student();
                student.setId(cursor.getInt(2));
                student.setName(cursor.getString(0));
                student.setEmail(cursor.getString(3));
                student.setPassword(cursor.getString(1));
                students.add(student);
            }

        }
        return students;

    }
    public byte[] get_student_solution(int student_id){
        byte[] image=null;
        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT_SOLUTION,
                new String[]{DataHelper.COL_SS_FILE},
                DataHelper.COL_STU_ID + "=" + student_id,
                null, null, null, null);
        if(cursor.moveToFirst()){
            image = cursor.getBlob(0);
        }
        return image;
    }
}
