package model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Muhammad on 11-May-17.
 */

public class StudentModel {
    DataHelper dataHelper;
    SQLiteDatabase sql;
    SharedPreferences sharedPreferences;
    Context context;

    public StudentModel(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
        sharedPreferences = context.getSharedPreferences("student", Context.MODE_PRIVATE);
        sql = dataHelper.getWritableDatabase();
    }

    public void addStudent(Student student) {
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DataHelper.COL_STU_ID, student.getId());
            contentValues.put(DataHelper.COL_STU_NAME, student.getName());
            contentValues.put(DataHelper.COL_STU_PASSWORD, student.getPassword());
            contentValues.put(DataHelper.COL_STU_EMAIL, student.getEmail());

            sql.insertOrThrow(DataHelper.TABLE_STUDENT, null, contentValues);
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException ex) {
            Toast.makeText(context, "Already Added.\nplease, try again.", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateStudent(Student student) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataHelper.COL_STU_NAME, student.getName());
        contentValues.put(DataHelper.COL_STU_PASSWORD, student.getPassword());
        contentValues.put(DataHelper.COL_STU_EMAIL, student.getEmail());

        sql.update(DataHelper.TABLE_STUDENT, contentValues,
                DataHelper.COL_STU_ID + "=" + student.getId(), null);
        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

    }

    public void student_update(Student student) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataHelper.COL_STU_NAME, student.getName());
        contentValues.put(DataHelper.COL_STU_PASSWORD, student.getPassword());

        sql.update(DataHelper.TABLE_STUDENT, contentValues, DataHelper.COL_STU_ID + "=" + student.getId(), null);
        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

    }

    public void deleteStudent(Student student) {
        sql.delete(DataHelper.TABLE_STUDENT, DataHelper.COL_STU_ID + "=" + student.getId(), null);
        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<Student> getAllStudent() {
        //this if you want to get all students
        Student student ;
        ArrayList<Student> list = new ArrayList<>();
        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT,
                new String[]{DataHelper.COL_STU_NAME, DataHelper.COL_STU_ID},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            student = new Student();
            student.setName(cursor.getString(0));
            student.setId(cursor.getInt(1));
            list.add(student);
        }
        return list;
    }

    public boolean student_login(Student student) {
        String name = student.getName();
        String password = student.getPassword();

        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT,
                new String[]{DataHelper.COL_STU_NAME, DataHelper.COL_STU_PASSWORD,
                        DataHelper.COL_STU_ID,DataHelper.COL_STU_EMAIL}, "name=? and password=?",
                new String[]{name, password}, null, null, null);

        if (cursor.moveToFirst()){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", cursor.getInt(2));
            editor.putString("name", cursor.getString(0));
            editor.putString("password", cursor.getString(1));
            editor.putString("email", cursor.getString(3));
            editor.commit();
            return true;
        }
        return false;
    }


}
