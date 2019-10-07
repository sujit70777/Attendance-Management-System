package com.myclasshelper.sujit007.myclasshelper.ClassInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.R;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 11/25/2016.
 */

public class ClassAdapter extends BaseAdapter {
    private Context context;
    private  ArrayList<ClassInfo> classInfoArrayList ;
    private static LayoutInflater inflater = null;
    private  ClassInfo classInfo = new ClassInfo();

    public ClassAdapter(Context context, ArrayList<ClassInfo> classInfoArrayList) {
        this.context = context;
        this.classInfoArrayList = classInfoArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return classInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int get_id(int position){

        classInfo = classInfoArrayList.get(position);

        return classInfo.getId();
    }

    public String get_name(int position){

        classInfo = classInfoArrayList.get(position);

        return classInfo.getClassName();
    }
    public String get_des(int position){

        classInfo = classInfoArrayList.get(position);

        return classInfo.getClassDetails();
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



            convertView = inflater.inflate(R.layout.l__class_row, null);

            TextView className = (TextView) convertView.findViewById(R.id.classNameLV_TV);
            TextView classDetails = (TextView)convertView.findViewById(R.id.classDetailsLV_TV);


            classInfo = classInfoArrayList.get(position);

            className.setText(classInfo.getClassName());
            classDetails.setText(classInfo.getClassDetails());



        return convertView;
    }
}
