package com.myclasshelper.sujit007.myclasshelper.StudentInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myclasshelper.sujit007.myclasshelper.DbHelper;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 12/1/2016.
 */

public class StudentDatabaseAdapter {
   DbHelper dbHelper ;

    public StudentDatabaseAdapter(Context context){

        dbHelper = DbHelper.getDbHelper(context);

    }

    // -------------------- INSERTING DATA IN THE STUDENT INFORMATION TABLE ------------

    public long StudentInfoInsert(String sid , String name , String classtype){
        long id;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.Student_Class_id , sid);
        contentValues.put(DbHelper.Student_Name , name);
        contentValues.put(DbHelper.Student_ClassTypeID , classtype);

        id = db.insert(DbHelper.Student_Information_TableName , null , contentValues);

        db.close();

        return id;
    }



    // ------------------- GETTING ALL THE DATA FROM THE Student TABLE ---------------

    public ArrayList<StudentInfo> getStudentData(String Classtype){
        ArrayList<StudentInfo> studentInfoArrayList = new ArrayList<StudentInfo>();
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String[] strings = {DbHelper.Student_id,DbHelper.Student_Class_id , DbHelper.Student_Name};
        String[] clstyp = {Classtype};

        Cursor cursor = db.query(DbHelper.Student_Information_TableName , strings ,DbHelper.Student_ClassTypeID + " = ?" ,clstyp, null ,null , null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.Student_id));
            int class_id = cursor.getInt(cursor.getColumnIndex(DbHelper.Student_Class_id));
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.Student_Name));
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudent_id(id);
            studentInfo.setStudent_Class_id(class_id);
            studentInfo.setStudent_Name(name);

            Log.v("DBHelper: ", "Name: "+id + "  " + class_id);
            Log.v("DBHelper: ", "Details: " + name);

            studentInfoArrayList.add(studentInfo);

        }
        db.close();
        cursor.close();
        return studentInfoArrayList;

    }



    // -------------------- DELETING DATA FROM CLASS TABLE ---------


    public void deleteStudent(String id ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        db.delete(DbHelper.Student_Information_TableName , DbHelper.Student_id + " = ?" , cid);
        db.close();
    }

    //------------------------UPDATING DATA FROM CLASS TABLE --------------

    public void updateStudent(String sid , String name , String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.Student_Class_id , sid);
        contentValues.put(DbHelper.Student_Name , name);

        db.update(DbHelper.Student_Information_TableName ,contentValues, DbHelper.Student_id + " = ?" , cid);

        db.close();
    }




}
