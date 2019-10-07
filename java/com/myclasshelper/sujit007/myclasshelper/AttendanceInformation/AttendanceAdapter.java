package com.myclasshelper.sujit007.myclasshelper.AttendanceInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.R;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 12/2/2016.
 */

public class AttendanceAdapter extends BaseAdapter {

    private ArrayList<AttendanceInfo> attendanceInfoArrayList = new ArrayList<AttendanceInfo>();
    private AttendanceInfo attendanceInfo = new AttendanceInfo();
    private static LayoutInflater inflater = null;
    private Context context;

    public AttendanceAdapter(Context context, ArrayList<AttendanceInfo> attendanceInfoArrayList) {
        this.context = context;
        this.attendanceInfoArrayList = attendanceInfoArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return attendanceInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int get_Std_Aid(int position){

        attendanceInfo = attendanceInfoArrayList.get(position);

        return attendanceInfo.getId();
    }

    public int get_Std_Cid(int position){

        attendanceInfo = attendanceInfoArrayList.get(position);

        return attendanceInfo.getStudent_id();
    }
    public String get_Std_A_Date(int position){

        attendanceInfo = attendanceInfoArrayList.get(position);

        return attendanceInfo.getAttendanceDate();
    }

    public String get_Std_A_Type(int position){

        attendanceInfo = attendanceInfoArrayList.get(position);

        return attendanceInfo.getClassAttendance();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.l__attendance_student, null);
        TextView StudentID = (TextView) convertView.findViewById(R.id.textView_attendance_roll);
        attendanceInfo = attendanceInfoArrayList.get(position);
        int id = attendanceInfo.getStudent_id();
        String cp = attendanceInfo.getClassAttendance();
        if(cp.equals("A")){
            convertView.setBackgroundColor(0xffff4444);
        }
        if(cp.equals("P")){
            convertView.setBackgroundColor(0xff99cc00);
        }
        if(cp.equals("L")){
            convertView.setBackgroundColor(0xffffbb33);

        }


        StudentID.setText(String.valueOf(id));
        return convertView;
    }
}
