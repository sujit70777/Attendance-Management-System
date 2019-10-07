package com.myclasshelper.sujit007.myclasshelper.AttendanceInformation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myclasshelper.sujit007.myclasshelper.R;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentInfo;

import java.util.ArrayList;

public class TakeAttendenceActivity extends AppCompatActivity {
    private Dialog dialog;
    private DatePicker datePicker;
    private TextView mydate;
    private String date = null, dateNum;
    private String ClassTypeID;
    private DatePickerDialog datePickerDialog;


    private StudentInfo studentInfo;
    private ArrayList<StudentInfo> studentInfoArrayList;
    private StudentDatabaseAdapter studentDatabaseAdapter;
    private StudentAdapter studentAdapter;
    private int size, size2;
    private int position;
    private ArrayList<AttendanceInfo> attendanceInfoArrayList;
    private AttendanceAdapter attendanceAdapter;
    private AttendanceDatabaseAdapter attendanceDatabaseAdapter;
    int atdID;
    String type;
    FloatingActionButton absent, present, leave;
    TextView mid, first, last;
    GridView attendanceGridView;
    boolean att = false;
    Button reset, undo;
    private static final String StudentAbsent = "A";
    private static final String StudentPresent = "P";
    private static final String StudentLeave = "L";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        mydate = (TextView) findViewById(R.id.mydate);
        absent = (FloatingActionButton) findViewById(R.id.floatingABSENT);
        present = (FloatingActionButton) findViewById(R.id.floatingPRESENT);
        leave = (FloatingActionButton) findViewById(R.id.floatingLEAVE);

        mid = (TextView) findViewById(R.id.textViewMid);
        last = (TextView) findViewById(R.id.textViewLast);
        first = (TextView) findViewById(R.id.textViewFirst);
        reset = (Button) findViewById(R.id.reset);
        undo = (Button) findViewById(R.id.undo);


        attendanceGridView = (GridView) findViewById(R.id.GridView_Attendence);

        dialog = new Dialog(TakeAttendenceActivity.this);
        dialog.setContentView(R.layout.d__get_date);
        datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);

        final Button getdate = (Button) dialog.findViewById(R.id.getdate);

        getdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = String.valueOf(datePicker.getDayOfMonth()) + "-" + String.valueOf(datePicker.getMonth() + 1) + "-" + String.valueOf(datePicker.getYear());
                dateNum = date;
                dialog.dismiss();
                mydate.setText("Date : " + date + "");
                setGridView();
                //  Toast.makeText(getApplicationContext(),  "" + dateNum, Toast.LENGTH_LONG).show();
            }
        });


        ClassTypeID = getIntent().getStringExtra("ClassTypeId");

        studentInfoArrayList = new ArrayList<StudentInfo>();
        studentDatabaseAdapter = new StudentDatabaseAdapter(this);
        studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);
        studentAdapter = new StudentAdapter(TakeAttendenceActivity.this, studentInfoArrayList);

        attendanceInfoArrayList = new ArrayList<AttendanceInfo>();
        attendanceDatabaseAdapter = new AttendanceDatabaseAdapter(this);


        size = studentInfoArrayList.size();
        position = 0;
        if (size > 0) {
            dialog.show();
        }

        SetStudentID();

        att = true;


        //   int id = studentAdapter.get_Std_Cid(1);
        //   Toast.makeText(getApplicationContext(),  "" + id, Toast.LENGTH_LONG).show();


        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAttendance(StudentPresent ,v);
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAttendance(StudentAbsent ,v);
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAttendance(StudentLeave ,v);
            }
        });


        //----------------------- UNDO AND RESET ----------------------


        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attendanceInfoArrayList.size() > 0 && attendanceInfoArrayList.size() < size) {
                    int aid = attendanceAdapter.get_Std_Aid(attendanceInfoArrayList.size() - 1);
                    attendanceDatabaseAdapter.deleteStudentAttendance(String.valueOf(aid));
                    position--;
                    setGridView();
                    SetStudentID();
                } else {
                    Snackbar.make(v, "No Attendance To Undo!!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (attendanceInfoArrayList.size() > 0) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(TakeAttendenceActivity.this);
                    builder.setTitle("Delete Class");
                    builder.setCancelable(false);
                    builder.setMessage("Are you sure to Reset All this Attendance ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            attendanceDatabaseAdapter.deleteStudentsAttendance(dateNum, ClassTypeID);
                            position = 0;
                            setGridView();
                            SetStudentID();
                            dialog.cancel();
                            Snackbar.make(v, "Attendance is Reset ", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
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


                } else {
                    Snackbar.make(v, "No Attendance To Reset!!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

            }
        });

        attendanceGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                type = attendanceAdapter.get_Std_A_Type(position);
                int CLASSid = attendanceAdapter.get_Std_Cid(position);
                atdID = attendanceAdapter.get_Std_Aid(position);
                //      Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
                dialog = new Dialog(TakeAttendenceActivity.this);
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
                        Toast.makeText(getApplicationContext(), "Attendance Updated to "+tt, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "Attendance Updated to "+tt, Toast.LENGTH_LONG).show();
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

    private void InsertAttendance(String str , View view) {
        if (position < size && size2 < size) {
            String sid = String.valueOf(studentAdapter.get_Std_Cid(position));

            long id = attendanceDatabaseAdapter.StudentInfoInsert(sid, dateNum, ClassTypeID, str);
            setGridView();
            position++;
            SetStudentID();
        } else {
            Snackbar.make(view, "Attendance is finish!!", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
          // Toast.makeText(getApplicationContext(), "Attendance is finish", Toast.LENGTH_LONG).show();
            setGridView();

        }


    }

    private void SetStudentID() {
        if (size > 0) {
            if (position < size) {
                mid.setText(String.valueOf(studentAdapter.get_Std_Cid(position)));
                if (position + 1 < size) {
                    last.setText("N: " + String.valueOf(studentAdapter.get_Std_Cid(position + 1)));
                } else {
                    last.setText("  ");
                }
                if (position - 1 > -1) {
                    first.setText("P: " + String.valueOf(studentAdapter.get_Std_Cid(position - 1)));
                } else {
                    first.setText("  ");
                }
            } else {
                mid.setText("");
            }


        } else {
            Toast.makeText(getApplicationContext(), "Add Students First", Toast.LENGTH_LONG).show();
        }
    }

    private void setGridView() {
        attendanceInfoArrayList = attendanceDatabaseAdapter.getAttendanceData(ClassTypeID, dateNum);
        size2 = attendanceInfoArrayList.size();
        if (size2 > size) {

            Toast.makeText(getApplicationContext(), "Attendance is Already taken", Toast.LENGTH_LONG).show();
            att = false;
        }
        attendanceAdapter = new AttendanceAdapter(TakeAttendenceActivity.this, attendanceInfoArrayList);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            attendanceGridView.setFastScrollEnabled(true);
        }
        attendanceGridView.setAdapter(attendanceAdapter);
    }
}
