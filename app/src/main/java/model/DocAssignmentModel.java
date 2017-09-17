package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Muhammad on 12-May-17.
 */

public class DocAssignmentModel {
    DataHelper dataHelper;
    SQLiteDatabase sql;
    Context context;


    public DocAssignmentModel(Context context) {
        dataHelper = new DataHelper(context);
        this.context = context;
        sql = dataHelper.getWritableDatabase();
    }

    public void add_doc_assignment(DocAssignment docAssignment) {
        ContentValues contentValues = new ContentValues();

//        contentValues.put(DataHelper.COL_DOC_ASSIGN_ID, docAssignment.getId());

        contentValues.put(DataHelper.COL_DOC_ASSIGN_DESC, docAssignment.getDesc());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_INSTRUCTION, docAssignment.getInstruction());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_START, docAssignment.getStart_time());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_END, docAssignment.getEnd_time());

        contentValues.put(DataHelper.COL_DOC_ASSIGN_FILE, docAssignment.getFile());

        contentValues.put(DataHelper.COL_DOC_ASSIGN_COURSE_ID, docAssignment.getCourse_id());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_DOC_ID, docAssignment.getDoc_id());

        sql.insert(DataHelper.TABLE_DOCTOR_ASSIGNMENT, null, contentValues);
        Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

    }

    public void update_doc_assignment(DocAssignment docAssignment) {
        ContentValues contentValues = new ContentValues();


        contentValues.put(DataHelper.COL_DOC_ASSIGN_DESC, docAssignment.getDesc());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_INSTRUCTION, docAssignment.getInstruction());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_START, docAssignment.getStart_time());
        contentValues.put(DataHelper.COL_DOC_ASSIGN_END, docAssignment.getEnd_time());

        contentValues.put(DataHelper.COL_DOC_ASSIGN_FILE, "" + docAssignment.getFile());


        sql.update(DataHelper.TABLE_DOCTOR_ASSIGNMENT, contentValues, DataHelper.COL_DOC_ASSIGN_ID + "=" + docAssignment.getId(), null);
    }

    public void delete_doc_assignment(DocAssignment docAssignment) {
        sql.delete(DataHelper.TABLE_DOCTOR_ASSIGNMENT, DataHelper.COL_DOC_ASSIGN_ID + "=" + docAssignment.getId(), null);
    }

    public boolean check_duration(DocAssignment docAssignment) {
        Calendar start_calendar = Calendar.getInstance();
        Calendar end_calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date start = format.parse(docAssignment.getStart_time());
            Date end = format.parse(docAssignment.getEnd_time());
            if (start.before(end))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<DocAssignment> getDoc_assignment(int doc_id) {
        ArrayList<DocAssignment> docAssignments = new ArrayList<>();
        DocAssignment docAssignment;


        Cursor cursor = sql.query(DataHelper.TABLE_DOCTOR_ASSIGNMENT,
                new String[]{DataHelper.COL_DOC_ASSIGN_ID, DataHelper.COL_DOC_ASSIGN_DESC},
                DataHelper.COL_DOC_ASSIGN_DOC_ID + "=" + doc_id, null, null, null, null);

        while (cursor.moveToNext()) {

            docAssignment = new DocAssignment();
            docAssignment.setId(cursor.getInt(0));
            docAssignment.setDesc(cursor.getString(1));

            docAssignments.add(docAssignment);
        }

        return docAssignments;
    }

    public ArrayList<DocAssignment> getCourse_assignment(int course_id) {
        ArrayList<DocAssignment> docAssignments = new ArrayList<>();
        DocAssignment docAssignment;


        Cursor cursor = sql.query(DataHelper.TABLE_DOCTOR_ASSIGNMENT,
                new String[]{DataHelper.COL_DOC_ASSIGN_ID, DataHelper.COL_DOC_ASSIGN_DESC,DataHelper.COL_DOC_ASSIGN_INSTRUCTION
        ,DataHelper.COL_DOC_ASSIGN_START,DataHelper.COL_DOC_ASSIGN_END,DataHelper.COL_DOC_ASSIGN_FILE,DataHelper.COL_DOC_ASSIGN_COURSE_ID
        ,DataHelper.COL_DOC_ASSIGN_DOC_ID},
                DataHelper.COL_DOC_ASSIGN_COURSE_ID + "=" + course_id, null, null, null, null);

        while (cursor.moveToNext()) {

            docAssignment = new DocAssignment();

            docAssignment.setId(cursor.getInt(0));
            docAssignment.setDesc(cursor.getString(1));
            docAssignment.setInstruction(cursor.getString(2));
            docAssignment.setStart_time(cursor.getString(3));
            docAssignment.setEnd_time(cursor.getString(4));
            docAssignment.setCourse_id(cursor.getInt(6));
            docAssignment.setDoc_id(cursor.getInt(7));

            docAssignments.add(docAssignment);
        }

        return docAssignments;
    }
    public DocAssignment get_single_assignment(int assignment_id){
        DocAssignment docAssignment = new DocAssignment();

        Cursor cursor = sql.query(DataHelper.TABLE_DOCTOR_ASSIGNMENT,
                new String[]{DataHelper.COL_DOC_ASSIGN_ID,
                        DataHelper.COL_DOC_ASSIGN_DESC,
                        DataHelper.COL_DOC_ASSIGN_INSTRUCTION
                        ,DataHelper.COL_DOC_ASSIGN_START,
                        DataHelper.COL_DOC_ASSIGN_END
                        ,DataHelper.COL_DOC_ASSIGN_FILE
                        ,DataHelper.COL_DOC_ASSIGN_COURSE_ID
                        ,DataHelper.COL_DOC_ASSIGN_DOC_ID},
                DataHelper.COL_DOC_ASSIGN_ID + "=" + assignment_id, null, null, null, null);
        Log.d("ddd","ccc "+assignment_id);

        if (cursor.moveToFirst()) {

            docAssignment.setId(cursor.getInt(0));
            docAssignment.setDesc(cursor.getString(1));
            docAssignment.setInstruction(cursor.getString(2));
            docAssignment.setStart_time(cursor.getString(3));
            docAssignment.setEnd_time(cursor.getString(4));
            docAssignment.setFile(cursor.getBlob(5));
            docAssignment.setCourse_id(cursor.getInt(6));
            docAssignment.setDoc_id(cursor.getInt(7));

            Log.d("ddd","bbb "+docAssignment.getDesc());

        }
        return docAssignment;
    }
}
