package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Muhammad on 12-May-17.
 */

public class CourseModel {

    DataHelper dataHelper;
    SQLiteDatabase sql;
    Context context;

    public CourseModel(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
        sql = dataHelper.getWritableDatabase();
    }

    public void addCourse(Course course) {
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DataHelper.COL_COURSE_ID, course.getId());
            contentValues.put(DataHelper.COL_COURSE_NAME, course.getName());
            contentValues.put(DataHelper.COL_COURSE_POINT, course.getPoint());
            contentValues.put(DataHelper.COL_COURSE_SEMESTER, course.getSemester());
            contentValues.put(DataHelper.COL_COURSE_DOC_ID, course.getDoc_id());

            sql.insertOrThrow(DataHelper.TABLE_COURSE, null, contentValues);
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException ex) {
            Toast.makeText(context, "Already added.\nplease, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void update_course(Course course) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DataHelper.COL_COURSE_ID, course.getId());
        contentValues.put(DataHelper.COL_COURSE_NAME, course.getName());
        contentValues.put(DataHelper.COL_COURSE_POINT, course.getPoint());
        contentValues.put(DataHelper.COL_COURSE_SEMESTER, course.getSemester());
        contentValues.put(DataHelper.COL_COURSE_DOC_ID, course.getDoc_id());

        sql.update(DataHelper.TABLE_COURSE, contentValues,
                DataHelper.COL_COURSE_ID + "=" + course.getId(), null);

        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();


    }

    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> courses = new ArrayList<>();
        Course course;

        Cursor cursor = sql.query(DataHelper.TABLE_COURSE,
                new String[]{DataHelper.COL_COURSE_NAME, DataHelper.COL_COURSE_ID,
                        DataHelper.COL_COURSE_POINT,DataHelper.COL_COURSE_SEMESTER
                        ,DataHelper.COL_COURSE_DOC_ID},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            course = new Course();

            course.setName(cursor.getString(0));
            course.setId(cursor.getInt(1));
            course.setPoint(cursor.getInt(2));
            course.setSemester(cursor.getString(3));
            course.setDoc_id(cursor.getInt(5));

            courses.add(course);
        }
        return courses;
    }

    public ArrayList<Course> getDoctor_course(Doctor doctor) {
        ArrayList<Course> courses = new ArrayList<>();
        Course course;


        Cursor cursor = sql.query(DataHelper.TABLE_COURSE,
                new String[]{
                        DataHelper.COL_COURSE_NAME,
                        DataHelper.COL_COURSE_ID,
                        DataHelper.COL_COURSE_POINT,
                        DataHelper.COL_COURSE_SEMESTER},
                DataHelper.COL_COURSE_DOC_ID + "=" + doctor.getId(), null, null, null, null);

        while (cursor.moveToNext()) {
            course = new Course();

            course.setName(cursor.getString(0));
            course.setId(cursor.getInt(1));
            course.setPoint(cursor.getInt(2));
            course.setSemester(cursor.getString(3));

            courses.add(course);
        }

        return courses;
    }

    public Course get_single_course(int id){
        Course course = new Course();
        Cursor cursor = sql.query(DataHelper.TABLE_COURSE,
                new String[]{DataHelper.COL_COURSE_NAME, DataHelper.COL_COURSE_ID,
                DataHelper.COL_COURSE_POINT,DataHelper.COL_COURSE_SEMESTER,DataHelper.COL_COURSE_DOC_ID},

                DataHelper.COL_COURSE_ID + "=" + id, null, null, null, null);

        if (cursor.moveToNext()) {
            course.setName(cursor.getString(0));
            course.setId(cursor.getInt(1));
            course.setPoint(cursor.getInt(2));
            course.setSemester(cursor.getString(3));
            course.setDoc_id(cursor.getInt(4));
        }
        return course;
    }

    public void delete_course(Course course) {
        sql.delete(DataHelper.TABLE_COURSE, DataHelper.COL_COURSE_ID + "=" + course.getId(), null);
        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();

    }


}
