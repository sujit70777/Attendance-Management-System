package com.myclasshelper.sujit007.myclasshelper.ClassInformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.AttendanceInfo;
import com.myclasshelper.sujit007.myclasshelper.AttendanceInformation.TakeAttendenceActivity;
import com.myclasshelper.sujit007.myclasshelper.MainActivity;
import com.myclasshelper.sujit007.myclasshelper.R;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.AddStudent;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentInfo;
import com.myclasshelper.sujit007.myclasshelper.StudentListActivity;
import com.myclasshelper.sujit007.myclasshelper.TakingClassAdapter;
import com.myclasshelper.sujit007.myclasshelper.TakingClassList;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    ImageButton addStudents, attendence , allClass , Allstudents;
    int ClassID;
    TextView num , numClass;
    private ArrayList<StudentInfo> studentInfoArrayList;
    private StudentAdapter studentAdapter;
    private StudentDatabaseAdapter studentDatabaseAdapter;

    private AttendanceDatabaseAdapter databaseAdapter;
    private TakingClassAdapter takingClassAdapter;
    private ArrayList<AttendanceInfo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        Bundle bundle = getIntent().getExtras();


        String className = bundle.getString("ClassName");
        ClassID = bundle.getInt("ClassID");
        //   Toast.makeText(getApplicationContext(), ClassID+" bb", Toast.LENGTH_LONG).show();
        TextView clsname = (TextView) findViewById(R.id.clsName);
        clsname.setText(className);

        //  Toast.makeText(getApplicationContext(), className+" o" , Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);


        num = (TextView) findViewById(R.id.NumberOfStudent);
        numClass = (TextView) findViewById(R.id.numClass);
        studentInfoArrayList = new ArrayList<StudentInfo>();
        studentDatabaseAdapter = new StudentDatabaseAdapter(this);
        studentInfoArrayList = studentDatabaseAdapter.getStudentData(String.valueOf(ClassID));
        studentAdapter = new StudentAdapter(this , studentInfoArrayList);
        arrayList = new ArrayList<AttendanceInfo>();
        databaseAdapter = new AttendanceDatabaseAdapter(this);
        arrayList = databaseAdapter.getTakingClasses(String.valueOf(ClassID));
        takingClassAdapter = new TakingClassAdapter(this, arrayList);

        num.setText(String.valueOf(studentAdapter.getCount()));
        numClass.setText(String.valueOf(takingClassAdapter.getCount()));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassActivity.this, MainActivity.class));
            }
        });

        addStudents = (ImageButton) findViewById(R.id.ibAddStudent);
        attendence = (ImageButton) findViewById(R.id.ibAttendence);
        allClass = (ImageButton) findViewById(R.id.ibAllClass);
        Allstudents = (ImageButton) findViewById(R.id.ibAllstudents);
        addStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, AddStudent.class);
                intent.putExtra("ClassTypeId", String.valueOf(ClassID));
                startActivity(intent);

            }
        });
        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, TakeAttendenceActivity.class);
                intent.putExtra("ClassTypeId", String.valueOf(ClassID));
                startActivity(intent);

            }
        });

        allClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, TakingClassList.class);
                intent.putExtra("ClassTypeId", String.valueOf(ClassID));
                startActivity(intent);
            }
        });

         Allstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, StudentListActivity.class);
                intent.putExtra("ClassTypeId", String.valueOf(ClassID));
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}

