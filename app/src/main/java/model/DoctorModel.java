package model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Muhammad on 11-May-17.
 */

public class DoctorModel {
    DataHelper dataHelper;
    SQLiteDatabase sql;
    SharedPreferences sharedPreferences;
    Context context;

    public DoctorModel(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
        sharedPreferences = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
        sql = dataHelper.getWritableDatabase();
    }

    public void addDoctor(Doctor doctor) throws SQLiteConstraintException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataHelper.COL_DOC_ID, doctor.getId());
            contentValues.put(DataHelper.COL_DOC_NAME, doctor.getName());
            contentValues.put(DataHelper.COL_DOC_PASSWORD, doctor.getPassword());
            contentValues.put(DataHelper.COL_DOC_EMAIL, doctor.getEmail());
            sql.insertOrThrow(DataHelper.TABLE_DOCTOR, null, contentValues);
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

        } catch (SQLiteConstraintException ex) {
            Toast.makeText(context, "Already added.\nplease, try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDoctor(Doctor doctor) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataHelper.COL_DOC_NAME, doctor.getName());
        contentValues.put(DataHelper.COL_DOC_PASSWORD, doctor.getPassword());
        contentValues.put(DataHelper.COL_DOC_EMAIL, doctor.getEmail());

        sql.update(DataHelper.TABLE_DOCTOR, contentValues, DataHelper.COL_DOC_ID + "=" + doctor.getId(), null);
        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

    }

    public void doctor_update(Doctor doctor) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataHelper.COL_DOC_NAME, doctor.getName());
        contentValues.put(DataHelper.COL_DOC_PASSWORD, doctor.getPassword());

        sql.update(DataHelper.TABLE_DOCTOR, contentValues, DataHelper.COL_DOC_ID + "=" + doctor.getId(), null);
        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

    }

    public void deleteDoctor(Doctor doctor) {
        sql.delete(DataHelper.TABLE_DOCTOR, DataHelper.COL_DOC_ID + "=" + doctor.getId(), null);
    }

    public boolean doctor_login(Doctor doctor) {
        String name = doctor.getName();
        String password = doctor.getPassword();
        Cursor cursor = sql.query(DataHelper.TABLE_DOCTOR,
                new String[]{DataHelper.COL_DOC_NAME, DataHelper.COL_DOC_PASSWORD,
                        DataHelper.COL_DOC_ID, DataHelper.COL_DOC_EMAIL},
                "name=? and password=?",
                new String[]{name, password}, null, null, null);


        if (cursor.moveToFirst()) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", cursor.getString(0));
            editor.putString("password", cursor.getString(1));
            editor.putInt("id", cursor.getInt(2));
            editor.putString("email", cursor.getString(3));
            editor.commit();
            return true;
        }
        return false;
    }

    public ArrayList<Doctor> getAllDoctors() {
        //this if you want to get all doctors
        Doctor doctor ;
        ArrayList<Doctor> list = new ArrayList<>();
        Cursor cursor = sql.query(DataHelper.TABLE_DOCTOR,
                new String[]{DataHelper.COL_DOC_NAME, DataHelper.COL_DOC_ID},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            doctor = new Doctor();
            doctor.setName(cursor.getString(0));
            doctor.setId(cursor.getInt(1));
            list.add(doctor);
        }
        return list;
    }
}
