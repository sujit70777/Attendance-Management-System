package com.myclasshelper.sujit007.myclasshelper.AttendanceInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myclasshelper.sujit007.myclasshelper.DbHelper;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 12/2/2016.
 */

public class AttendanceDatabaseAdapter {
    DbHelper dbHelper ;

    public AttendanceDatabaseAdapter(Context context){

        dbHelper = DbHelper.getDbHelper(context);

    }

    // -------------------- INSERTING DATA IN THE STUDENT INFORMATION TABLE ------------

    public long StudentInfoInsert(String sid , String date , String classtype_id , String attendance){
        long id;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.S_roll , sid);
        contentValues.put(DbHelper.AttendanceDate , date);
        contentValues.put(DbHelper.ClassTypeID , classtype_id);
        contentValues.put(DbHelper.ClassAttendance , attendance);

        id = db.insert(DbHelper.StudentAttendance , null , contentValues);

        db.close();

        return id;
    }


    // ------------------- GETTING ALL THE DATA FROM THE CLASS TABLE ---------------

    public ArrayList<AttendanceInfo> getAttendanceData(String Classtype , String cdate){
        ArrayList<AttendanceInfo> attendanceInfoArrayList = new ArrayList<AttendanceInfo>( );
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String[] strings = {DbHelper.S_id,DbHelper.S_roll , DbHelper.AttendanceDate ,DbHelper.ClassAttendance };
        String[] clstyp = {Classtype ,cdate};

        Cursor cursor = db.query(DbHelper.StudentAttendance , strings ,DbHelper.ClassTypeID + " = ? AND " +DbHelper.AttendanceDate + " = ?" ,clstyp, null ,null , null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.S_id));
            int class_id = cursor.getInt(cursor.getColumnIndex(DbHelper.S_roll));
            String date = cursor.getString(cursor.getColumnIndex(DbHelper.AttendanceDate));
            String attendance = cursor.getString(cursor.getColumnIndex(DbHelper.ClassAttendance));
          AttendanceInfo attendanceInfo = new AttendanceInfo();
            attendanceInfo.setId(id);
            attendanceInfo.setStudent_id(class_id);
            attendanceInfo.setAttendanceDate(date);
            attendanceInfo.setClassAttendance(attendance);


            Log.v("DBHelper: ", "Name: "+id + "  " + class_id);
            Log.v("DBHelper: ", "Details: " + attendance);

            attendanceInfoArrayList.add(attendanceInfo);

        }
        db.close();
        cursor.close();
        return attendanceInfoArrayList;

    }

    // -------------------- DELETING DATA FROM CLASS TABLE ---------


    public void deleteStudentAttendance(String id ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        db.delete(DbHelper.StudentAttendance , DbHelper.S_id + " = ?" , cid);
        db.close();
    }
    public void UndoStudentAttendance(String id , String date , String classId ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id , date , classId};

        db.delete(DbHelper.StudentAttendance , DbHelper.S_id + " = ? AND "+DbHelper.AttendanceDate + " = ? AND "+DbHelper.ClassTypeID+" = ?" , cid);
        db.close();
    }



    public void deleteStudentsAttendance(String date , String classId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {date , classId};

        db.delete(DbHelper.StudentAttendance , DbHelper.AttendanceDate + " = ? AND "+DbHelper.ClassTypeID+" = ?" , cid);
        db.close();
    }


    //------------------------UPDATING DATA FROM CLASS TABLE --------------

    public void updateStudentAttendance(String id , String pType){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ClassAttendance , pType);


        db.update(DbHelper.StudentAttendance ,contentValues, DbHelper.S_id + " = ?" , cid);

        db.close();
    }



    //-------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------
    // ------------------- GETTING ALL THE DATA FROM THE CLASS TABLE ---------------

    public ArrayList<AttendanceInfo> getTakingClasses(String Classtype){
        ArrayList<AttendanceInfo> attendanceInfoArrayList = new ArrayList<AttendanceInfo>( );
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String[] strings = { DbHelper.AttendanceDate };
        String[] clstyp = {Classtype};

        Cursor cursor = db.query( DbHelper.StudentAttendance , strings ,DbHelper.ClassTypeID + " = ? ",clstyp, DbHelper.AttendanceDate ,null , null);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(DbHelper.AttendanceDate));

            AttendanceInfo attendanceInfo = new AttendanceInfo();

            attendanceInfo.setAttendanceDate(date);
            attendanceInfoArrayList.add(attendanceInfo);

        }
        db.close();
        cursor.close();
        return attendanceInfoArrayList;

    }






}
