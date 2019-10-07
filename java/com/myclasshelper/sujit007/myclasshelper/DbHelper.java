package com.myclasshelper.sujit007.myclasshelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sujit007 on 11/25/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DatabaseName = "ClassDB";
    public static final int DatabaseVersion = 1;

    // Class Table Attributes
    public static final String ClassTable = "classTable";
    public static final String C_id = "c_id";
    public static final String ClassName = "className";
    public static final String ClassDetails = "classDetails";
    public static final String ClassQuery = "CREATE TABLE "+ClassTable+" ("+C_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "+ClassName+" TEXT NOT NULL, "+ClassDetails+" TEXT NOT NULL);";


    //StudentAttendance Table Attribute
    public static final String StudentAttendance = "studentAttendance";
    public static final String S_id = "s_id";
    public static final String S_roll = "s_roll";
    public static final String ClassTypeID = "classTypeId";
    public static final String AttendanceDate = "attendanceDate";
    public static final String ClassAttendance = "classAttendance";
    public static final String StudentAttendanceQuery = "CREATE TABLE "+StudentAttendance+" ( "+S_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "+S_roll+" INTEGER NOT NULL, "+ClassTypeID+" INTEGER NOT NULL, "+AttendanceDate+" TEXT NOT NULL, "+ClassAttendance+" TEXT NOT NULL);";


    // STUDENT INFORMATION TABLE ATTRIBUTES ANS QUERY
    public static final String Student_Information_TableName = "studentInformation";
    public static final String Student_id = "_id";
    public static final String Student_Class_id = "class_id";
    public static final String Student_Name = "name";
    public static final String Student_mobile = "mobile";
    public static final String Student_Total_Class = "total_class";
    public static final String Student_Class_Test1 = "class_test1";
    public static final String Student_Class_Test2 = "class_test2";
    public static final String Student_Assignment1 = "assignment1";
    public static final String Student_Assignment2 = "assignment2";
    public static final String Student_Mid_Exam = "mid_exam";
    public static final String Student_Final_Exam = "final_exam";
    public static final String Student_ClassTypeID = "classTypeId";
    public static final String Student_Information_Query = "CREATE TABLE "+Student_Information_TableName+" ( "+Student_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "+Student_Class_id+" INTEGER NOT NULL, "+Student_Name+" TEXT, "+Student_mobile+" TEXT, "+Student_Total_Class+" INTEGER, "+Student_Class_Test1+" INTEGER, "+Student_Class_Test2+" INTEGER, "+Student_Assignment1+" INTEGER, "+Student_Assignment2+" INTEGER, "+Student_Mid_Exam+" INTEGER, "+Student_Final_Exam+" INTEGER , "+Student_ClassTypeID+" INTEGER );";

    private static DbHelper dbHelper ;

    private DbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        Log.d("Table Created", "Database-------------");
    }

    public static DbHelper getDbHelper(Context context){
        if(dbHelper==null){
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassQuery);
        db.execSQL(StudentAttendanceQuery);
        db.execSQL(Student_Information_Query);

        Log.d("Table Created", "onCreate-------------");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
