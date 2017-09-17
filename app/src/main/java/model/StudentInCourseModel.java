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
 * Created by Muhammad on 22-May-17.
 */

public class StudentInCourseModel {
    DataHelper dataHelper;
    SQLiteDatabase sql;
    Context context;

    public StudentInCourseModel(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
        sql = dataHelper.getWritableDatabase();
    }

    public void add_s_in_c(StudentInCourse studentInCourse) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataHelper.COL_SINC_COURSE_ID, studentInCourse.getCourse_id());
            contentValues.put(DataHelper.COL_SINC_STU_ID, studentInCourse.getStudent_id());


            sql.insertOrThrow(DataHelper.TABLE_STUDENT_IN_COURSE, null, contentValues);

            Toast.makeText(context, "Yeah you enrolled Successfully", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException ex) {
            Toast.makeText(context, "Already added.\nplease, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Course> getStudent_course(int id) {
        ArrayList<Course> courses = new ArrayList<>();
        Course course;

        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT_IN_COURSE,
                new String[]{DataHelper.COL_SINC_COURSE_ID},
    DataHelper.COL_SINC_STU_ID + "=" + id,
            null, null, null, null);
        while (cursor.moveToNext()) {

            course = new Course();

            course.setId(cursor.getInt(0));
            courses.add(course);

        }
        return courses;
    }

    public ArrayList<Course> getEnrolled(int id){
        ArrayList<Course> courses_found = getStudent_course(id);
        ArrayList<Course> courses_enrolled = new ArrayList<>();
        Course course_en;
        for (Course course:courses_found){
            Cursor cursor = sql.query(DataHelper.TABLE_COURSE,
                    new String[]{DataHelper.COL_COURSE_ID,DataHelper.COL_COURSE_NAME},
                    DataHelper.COL_COURSE_ID + "=" + course.getId(),
                    null, null, null, null);
            if(cursor.moveToFirst()){
                course_en = new Course();
                course_en.setId(cursor.getInt(0));
                course_en.setName(cursor.getString(1));
                courses_enrolled.add(course_en);
            }
        }
        return courses_enrolled;
    }

    public ArrayList<Student> get_student_in_course(int id) {
        ArrayList<Student> students = new ArrayList<>();
        Student student;
        int stu_id = 0;
        boolean done = false;
        Cursor cursor = sql.query(DataHelper.TABLE_STUDENT_IN_COURSE,
                new String[]{DataHelper.COL_SINC_STU_ID},
                DataHelper.COL_SINC_COURSE_ID+ "=" + id, null, null, null, null);

        if (cursor.moveToFirst()){
            done = true;
            Log.d("fff","=" + cursor.getInt(0));
            stu_id = cursor.getInt(0);
        }
        Cursor cursor_student =sql.query(DataHelper.TABLE_STUDENT,
                new String[]{DataHelper.COL_STU_ID,
                        DataHelper.COL_STU_NAME,
                        DataHelper.COL_STU_PASSWORD,
                        DataHelper.COL_STU_EMAIL},
                DataHelper.COL_STU_ID+ "=" + stu_id, null, null, null, null);

        while (cursor_student.moveToNext()) {

            student = new Student();

            student.setId(cursor_student.getInt(0));
            student.setName(cursor_student.getString(1));
            student.setPassword(cursor_student.getString(2));
            student.setEmail(cursor_student.getString(3));

            students.add(student);
            Log.d("fff","=" + student.getName());

        }


        return students;
    }


}
