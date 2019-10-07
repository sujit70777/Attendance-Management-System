package com.myclasshelper.sujit007.myclasshelper.StudentInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.R;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 12/1/2016.
 */

public class StudentAdapter extends BaseAdapter {
    private ArrayList<StudentInfo> studentInfos = new ArrayList<StudentInfo>();
    private StudentInfo studentInfo = new StudentInfo();
    private static LayoutInflater inflater = null;
    private Context context;

    public StudentAdapter(Context context, ArrayList<StudentInfo> studentInfos) {
        this.context = context;
        this.studentInfos = studentInfos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int get_Std_id(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getStudent_id();
    }

    public int get_Std_Cid(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getStudent_Class_id();
    }
    public String get_Std_name(int position){

        studentInfo = studentInfos.get(position);

        return studentInfo.getStudent_Name();
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.l__student_row, null);

        TextView StudentID = (TextView) convertView.findViewById(R.id.StudentID_LV_TV);
        TextView StudentName = (TextView)convertView.findViewById(R.id.studentNameLV_TV);


        studentInfo = studentInfos.get(position);

        int id = studentInfo.getStudent_Class_id();



        StudentID.setText(String.valueOf(id));
        StudentName.setText(studentInfo.getStudent_Name());

        return convertView;

    }
}
