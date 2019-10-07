package com.myclasshelper.sujit007.myclasshelper.ClassInformation;

/**
 * Created by Sujit007 on 11/25/2016.
 */

public class ClassInfo {

    private int id;
    private String ClassName;
    private String ClassDetails;


    public ClassInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }





    public String getClassDetails() {

        return ClassDetails;
    }

    public void setClassDetails(String classDetails) {
        ClassDetails = classDetails;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }
}
