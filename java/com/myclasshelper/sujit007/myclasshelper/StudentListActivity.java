package com.myclasshelper.sujit007.myclasshelper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.StudentInformation.StudentInfo;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {


    ListView listView;
    private ArrayList<StudentInfo> studentInfoArrayList;
    private StudentAdapter studentAdapter;
    private StudentDatabaseAdapter studentDatabaseAdapter;
    String ClassTypeID;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ClassTypeID = getIntent().getStringExtra("ClassTypeId");
        dialog = new Dialog(StudentListActivity.this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.studentListNew);

        studentInfoArrayList = new ArrayList<StudentInfo>();
        studentDatabaseAdapter = new StudentDatabaseAdapter(this);
        studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);

        studentAdapter = new StudentAdapter(StudentListActivity.this, studentInfoArrayList);

        listView.setAdapter(studentAdapter);




    }

}
