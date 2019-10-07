package com.myclasshelper.sujit007.myclasshelper.AttendanceInformation;

/**
 * Created by Sujit007 on 12/2/2016.
 */

public class AttendanceInfo {

    private int id;
    private int Student_id;
    private int ClassTypeID ;
    private String AttendanceDate ;
    private String ClassAttendance ;

    public AttendanceInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(int student_id) {
        Student_id = student_id;
    }

    public int getClassTypeID() {
        return ClassTypeID;
    }

    public void setClassTypeID(int classTypeID) {
        ClassTypeID = classTypeID;
    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        AttendanceDate = attendanceDate;
    }

    public String getClassAttendance() {
        return ClassAttendance;
    }

    public void setClassAttendance(String classAttendance) {
        ClassAttendance = classAttendance;
    }
}
