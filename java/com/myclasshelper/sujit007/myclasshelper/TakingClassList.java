package com.myclasshelper.sujit007.myclasshelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceInfo;

import java.util.ArrayList;

public class TakingClassList extends AppCompatActivity {

    private AttendanceDatabaseAdapter databaseAdapter;
    private TakingClassAdapter takingClassAdapter;
    private ArrayList<AttendanceInfo> arrayList;
    private String ClassTypeID;
    ListView classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_class_list);

        ClassTypeID = getIntent().getStringExtra("ClassTypeId");
        arrayList = new ArrayList<AttendanceInfo>();
        databaseAdapter = new AttendanceDatabaseAdapter(this);
        classes = (ListView) findViewById(R.id.classesList);
        setClass();

        classes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                final String dateNum = takingClassAdapter.get_Take_class_Date(position);

                final AlertDialog.Builder builder = new AlertDialog.Builder(TakingClassList.this);
                builder.setTitle("Delete Class");
                builder.setCancelable(false);
                builder.setMessage("Are you sure to Delete the Class of "+dateNum+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseAdapter.deleteStudentsAttendance(dateNum, ClassTypeID);

                        dialog.cancel();
                        Snackbar.make(view, "Class is Deleted!! ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        setClass();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();





                return true;
            }
        });

        classes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dateN = takingClassAdapter.get_Take_class_Date(position);
                Bundle bundle = new Bundle();
                bundle.putString("ClassTypeID" , ClassTypeID);
                bundle.putString("DateOfClass" , dateN);
                Intent intent = new Intent(TakingClassList.this , TakingClassDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });




    }

    private void setClass() {
        arrayList = databaseAdapter.getTakingClasses(ClassTypeID);
        takingClassAdapter = new TakingClassAdapter(this, arrayList);
        classes.setAdapter(takingClassAdapter);

    }
}
