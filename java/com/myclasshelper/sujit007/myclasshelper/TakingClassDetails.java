package com.myclasshelper.sujit007.myclasshelper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceAdapter;
import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceInfo;

import java.util.ArrayList;

public class TakingClassDetails extends AppCompatActivity {
    private String ClassTypeID;
    private String ClassDate;
    private ArrayList<AttendanceInfo> attendanceInfoArrayList;
    private AttendanceAdapter attendanceAdapter;
    private AttendanceDatabaseAdapter attendanceDatabaseAdapter;
    GridView DetailsGrid;
    int atdID;
    String type;
    Dialog dialog;
    private int size;
    TextView presentP, absentP, leaveP , Total;
    float  a, p,sum , l;
    float pp , ap , lp;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taking_class_details);

        presentP = (TextView) findViewById(R.id.textViewPP);
        absentP = (TextView) findViewById(R.id.textViewAP);
        leaveP = (TextView) findViewById(R.id.textViewLP);
        Total = (TextView) findViewById(R.id.textViewTS);


        Bundle bundle = getIntent().getExtras();
        ClassTypeID = bundle.getString("ClassTypeID");
        ClassDate = bundle.getString("DateOfClass");
        attendanceInfoArrayList = new ArrayList<AttendanceInfo>();
        attendanceDatabaseAdapter = new AttendanceDatabaseAdapter(this);
        DetailsGrid = (GridView) findViewById(R.id.gridVTakeClasses);
        setGridView();

        DetailsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                type = attendanceAdapter.get_Std_A_Type(position);
                int CLASSid = attendanceAdapter.get_Std_Cid(position);
                atdID = attendanceAdapter.get_Std_Aid(position);
                //      Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
                dialog = new Dialog(TakingClassDetails.this);
                dialog.setContentView(R.layout.d__class_long_press);
                ImageView close = (ImageView) dialog.findViewById(R.id.closeImg);
                TextView text = (TextView) dialog.findViewById(R.id.myDialog);
                Button delete = (Button) dialog.findViewById(R.id.deletebtn);
                Button update = (Button) dialog.findViewById(R.id.updatebtn);
                dialog.setCancelable(false);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });
                if (type.equals("A")) {
                    delete.setText("Leave");
                    delete.setBackgroundColor(0xffffbb33);

                    update.setText("Present");
                    update.setBackgroundColor(0xff99cc00);

                    text.setText("" + CLASSid);
                    text.setTextSize((float) 40.0);
                    text.setTextColor(0xffffffff);
                    text.setBackgroundColor(0xffff4444);
                }

                if (type.equals("P")) {
                    delete.setText("Absent");
                    delete.setBackgroundColor(0xffff4444);

                    update.setText("Leave");
                    update.setBackgroundColor(0xffffbb33);

                    text.setText("" + CLASSid);
                    text.setTextSize((float) 40.0);
                    text.setTextColor(0xffffffff);
                    text.setBackgroundColor(0xff99cc00);
                }

                if (type.equals("L")) {
                    delete.setText("Absent");
                    delete.setBackgroundColor(0xffff4444);

                    update.setText("Present");
                    update.setBackgroundColor(0xff99cc00);

                    text.setText("" + CLASSid);
                    text.setTextSize((float) 40.0);
                    text.setTextColor(0xffffffff);
                    text.setBackgroundColor(0xffffbb33);
                }

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String send = null;
                        String tt = null;

                        if (type.equals("A")) {
                            send = "L";
                            tt = "Leave";
                        } else if (type.equals("P")) {
                            send = "A";
                            tt = "Absent";
                        } else if (type.equals("L")) {
                            send = "A";
                            tt = "Absent";
                        }


                        UpdateSattendance(send, atdID);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Attendance Updated to " + tt, Toast.LENGTH_LONG).show();
                    }
                });


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String send = null;

                        String tt = null;

                        if (type.equals("A")) {
                            send = "P";
                            tt = "Present";

                        } else if (type.equals("P")) {
                            send = "L";

                            tt = "Leave";

                        } else if (type.equals("L")) {
                            send = "P";
                            tt = "Present";
                        }
                        UpdateSattendance(send, atdID);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Attendance Updated to " + tt, Toast.LENGTH_LONG).show();
                    }
                });


                dialog.show();
            }
        });


    }

    private void UpdateSattendance(String typeOFad, int adID) {

        attendanceDatabaseAdapter.updateStudentAttendance(String.valueOf(adID), typeOFad);
        setGridView();
    }

    private void setGridView() {
        attendanceInfoArrayList = attendanceDatabaseAdapter.getAttendanceData(ClassTypeID, ClassDate);
        attendanceAdapter = new AttendanceAdapter(TakingClassDetails.this, attendanceInfoArrayList);
        DetailsGrid.setAdapter(attendanceAdapter);
        size = attendanceInfoArrayList.size();
        setPercent();
    }

    private void setPercent() {
        a = 0;
        p = 0;
        l = 0;
        pp = 0;
        lp = 0;
        ap = 0;


        for (i = 0; i < size; i++) {

            if (attendanceAdapter.get_Std_A_Type(i).equals("P")) {
                p++;
            } else if (attendanceAdapter.get_Std_A_Type(i).equals("A")) {
                a++;
            } else if (attendanceAdapter.get_Std_A_Type(i).equals("L")) {
                l++;
            }

        }
        sum = a+p+l;

        pp = sum/p;
        ap = sum/a;
        lp =sum/l;
        pp = 100/pp;
        ap = 100/ap;
        lp = 100/lp;

        Total.setText("Total Students: "+String.valueOf((int)sum) );
        presentP.setText("Total Present: "+String.valueOf((int)p) + "\nPercentage: "+String.valueOf(pp)+"%");
        absentP.setText("Total Absent: "+String.valueOf((int)a) +"\nPercentage: "+String.valueOf(ap)+"%");
        leaveP.setText("Total Leave: "+ String.valueOf((int)l) + "\nPercentage: "+String.valueOf(lp)+"%");
    }


}
