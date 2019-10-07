package com.myclasshelper.sujit007.myclasshelper.ClassInformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myclasshelper.sujit007.myclasshelper.DbHelper;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 11/25/2016.
 */

public class ClassDatabaseAdapter {

    DbHelper dbHelper ;

    public ClassDatabaseAdapter(Context context){

        dbHelper = DbHelper.getDbHelper(context);

    }


    // -------------------- INSERTING DATA IN THE CLASS INFORMATION TABLE ------------

    public long ClassInfoInsert(String name , String details){
        long id;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ClassName , name);
        contentValues.put(DbHelper.ClassDetails , details);

        id = db.insert(DbHelper.ClassTable , null , contentValues);

        db.close();

        return id;
    }


    // ------------------- GETTING ALL THE DATA FROM THE CLASS TABLE ---------------

    public ArrayList<ClassInfo> getClassData(){
        ArrayList<ClassInfo> classInfoArrayList = new ArrayList<ClassInfo>();
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String[] strings = {DbHelper.C_id,DbHelper.ClassName , DbHelper.ClassDetails};

        Cursor cursor = db.query(DbHelper.ClassTable , strings ,null ,null, null ,null , null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.C_id));
            String c_name = cursor.getString(cursor.getColumnIndex(DbHelper.ClassName));
            String c_details = cursor.getString(cursor.getColumnIndex(DbHelper.ClassDetails));
            ClassInfo classInfo = new ClassInfo();
            classInfo.setId(id);
            classInfo.setClassName(c_name);
            classInfo.setClassDetails(c_details);

            Log.v("DBHelper: ", "Name: "+id + "  " + c_name);
            Log.v("DBHelper: ", "Details: " + c_name);

            classInfoArrayList.add(classInfo);

        }
        db.close();
        cursor.close();
        return classInfoArrayList;

    }


    // -------------------- DELETING DATA FROM CLASS TABLE ---------


    public void deleteClass(String id ){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        db.delete(DbHelper.ClassTable , DbHelper.C_id + " = ?" , cid);
        db.close();
    }

    //------------------------UPDATING DATA FROM CLASS TABLE --------------

    public void updateClass(String name , String des , String id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] cid = {id};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ClassName , name);
        contentValues.put(DbHelper.ClassDetails , des);

        db.update(DbHelper.ClassTable ,contentValues, DbHelper.C_id + " = ?" , cid);

        db.close();
    }

}
