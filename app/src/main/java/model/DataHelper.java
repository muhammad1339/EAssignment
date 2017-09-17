package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Muhammad on 10-May-17.
 */

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "eassignment";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_DOCTOR = "doctor";
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_COURSE = "course";

    public static final String TABLE_STUDENT_IN_COURSE = "studentincourse";
    public static final String TABLE_DOCTOR_ASSIGNMENT = "docassignment";
    public static final String TABLE_STUDENT_SOLUTION = "studentsolution";

    public static final String COL_DOC_ID = "id";
    public static final String COL_DOC_NAME = "name";
    public static final String COL_DOC_EMAIL = "email";
    public static final String COL_DOC_PASSWORD = "password";

    public static final String COL_STU_ID = "id";
    public static final String COL_STU_NAME = "name";
    public static final String COL_STU_EMAIL = "email";
    public static final String COL_STU_PASSWORD = "password";

    public static final String COL_COURSE_ID = "id";
    public static final String COL_COURSE_NAME = "name";
    public static final String COL_COURSE_POINT = "point";
    public static final String COL_COURSE_SEMESTER = "semester";
    public static final String COL_COURSE_DOC_ID = "doc_id";

    public static final String COL_SINC_STU_ID = "stu_id";
    public static final String COL_SINC_COURSE_ID = "course_id";

    public static final String COL_DOC_ASSIGN_ID = "id";
    public static final String COL_DOC_ASSIGN_FILE = "file";
    public static final String COL_DOC_ASSIGN_DESC = "desc";
    public static final String COL_DOC_ASSIGN_INSTRUCTION = "instruction";
    public static final String COL_DOC_ASSIGN_START = "start";
    public static final String COL_DOC_ASSIGN_END = "end";
    public static final String COL_DOC_ASSIGN_DOC_ID = "doc_id";
    public static final String COL_DOC_ASSIGN_COURSE_ID = "course_id";

    public static final String COL_SS_STU_ID = "id";
    public static final String COL_SS_ASSIGN_ID = "assign_id";
    public static final String COL_SS_FILE = "file";

    public static final String CREATE_DOCTOR = "CREATE TABLE " + TABLE_DOCTOR + " ( " + COL_DOC_ID + " INTEGER PRIMARY KEY " +
            "," + COL_DOC_NAME + " TEXT UNIQUE," + COL_DOC_EMAIL + " TEXT UNIQUE, " + COL_DOC_PASSWORD + " TEXT )";

    public static final String CREATE_STUDENT = "CREATE TABLE " + TABLE_STUDENT + " ( " + COL_STU_ID + " INTEGER PRIMARY KEY " +
            "," + COL_STU_NAME + " TEXT UNIQUE," + COL_STU_EMAIL + " TEXT UNIQUE, " + COL_STU_PASSWORD + " TEXT )";

    public static final String CREATE_COURSE = "CREATE TABLE " + TABLE_COURSE +
            "(" + COL_COURSE_ID + " INTEGER PRIMARY KEY , " + COL_COURSE_NAME + " TEXT UNIQUE,"
            + COL_COURSE_POINT + " INTEGER ," + COL_COURSE_SEMESTER + " TEXT, " + COL_COURSE_DOC_ID + " INTEGER ,"
            + "FOREIGN KEY (" + COL_COURSE_DOC_ID + ") REFERENCES " + TABLE_DOCTOR + "(" + COL_DOC_ID + ") )";


    public static final String CREATE_DOCTOR_ASSIGNMENT = "CREATE TABLE " + TABLE_DOCTOR_ASSIGNMENT + "( "
            + COL_DOC_ASSIGN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_DOC_ASSIGN_FILE + " BLOB," +
            COL_DOC_ASSIGN_DESC + " TEXT UNIQUE," + COL_DOC_ASSIGN_INSTRUCTION + " TEXT," +
            COL_DOC_ASSIGN_START + " DATE," + COL_DOC_ASSIGN_END + " DATE," + COL_DOC_ASSIGN_DOC_ID + " INTEGER,"
            + COL_DOC_ASSIGN_COURSE_ID + " INTEGER," +
            "FOREIGN KEY (" + COL_DOC_ASSIGN_DOC_ID + ") REFERENCES " + TABLE_DOCTOR + "(" + COL_DOC_ID + ") ," +
            "FOREIGN KEY (" + COL_DOC_ASSIGN_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COL_COURSE_ID + "))";

    public static final String CREATE_STUDENT_IN_COURSE = "CREATE TABLE " + TABLE_STUDENT_IN_COURSE + "( " + COL_SINC_STU_ID + " INTEGER ,"
            + COL_SINC_COURSE_ID + " INTEGER ," +
            "FOREIGN KEY (" + COL_SINC_STU_ID + ") REFERENCES " + TABLE_STUDENT + "(" + COL_STU_ID + ")," +
            "FOREIGN KEY (" + COL_SINC_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COL_COURSE_ID + "),"+
            "PRIMARY KEY ("+COL_SINC_STU_ID+","+COL_SINC_COURSE_ID+"))";

    public static final String CREATE_STUDENT_SOLUTION = "CREATE TABLE " + TABLE_STUDENT_SOLUTION + "(" +
            COL_SS_STU_ID + " INTEGER , " + COL_SS_ASSIGN_ID + " INTEGER , " + COL_SS_FILE + " BLOB," +
            "FOREIGN KEY (" + COL_SS_STU_ID + ") REFERENCES " + TABLE_STUDENT + "(" + COL_STU_ID + ")," +
            "FOREIGN KEY (" + COL_SS_ASSIGN_ID + ") REFERENCES " + TABLE_DOCTOR_ASSIGNMENT + "(" + COL_DOC_ASSIGN_ID + "),"+
            "PRIMARY KEY ("+COL_SS_ASSIGN_ID+","+COL_SS_STU_ID+"))";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DOCTOR);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_STUDENT_IN_COURSE);
        db.execSQL(CREATE_DOCTOR_ASSIGNMENT);
        db.execSQL(CREATE_STUDENT_SOLUTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
