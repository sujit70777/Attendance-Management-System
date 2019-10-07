package com.myclasshelper.sujit007.myclasshelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceInfo;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 12/5/2016.
 */

public class TakingClassAdapter extends BaseAdapter {

    private ArrayList<AttendanceInfo> attendanceInfoArrayList = new ArrayList<AttendanceInfo>();
    private AttendanceInfo attendanceInfo = new AttendanceInfo();
    private static LayoutInflater inflater = null;
    private Context context;

    public TakingClassAdapter(Context context, ArrayList<AttendanceInfo> attendanceInfoArrayList) {
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

    public String get_Take_class_Date(int position){

        attendanceInfo = attendanceInfoArrayList.get(position);

        return attendanceInfo.getAttendanceDate();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.l__classlistrow, null);
        TextView StudentID = (TextView) convertView.findViewById(R.id.ClassNameListRow);
        attendanceInfo = attendanceInfoArrayList.get(position);
       String date = attendanceInfo.getAttendanceDate();

        StudentID.setText(String.valueOf(date));


        return convertView;
    }
}
