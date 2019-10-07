package com.myclasshelper.sujit007.myclasshelper.StudentInformation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.R;

import java.util.ArrayList;

public class AddStudent extends AppCompatActivity {
    EditText addid, addName;
    Button addbtn;
    String id, name;
    private Dialog dialog;
    Button D_save, D_cancel;

    EditText classN, descp;
    TextView nam, dets;
    private ArrayList<StudentInfo> studentInfoArrayList;
    private StudentAdapter studentAdapter;
    private StudentDatabaseAdapter studentDatabaseAdapter;
    ListView listView;
    String ClassTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ClassTypeID = getIntent().getStringExtra("ClassTypeId");

        //  Toast.makeText(getApplicationContext(), ClassTypeID+" bb", Toast.LENGTH_LONG).show();
        dialog = new Dialog(AddStudent.this);
        addbtn = (Button) findViewById(R.id.saveStudent);
        addid = (EditText) findViewById(R.id.stdAddID);
        addName = (EditText) findViewById(R.id.stdAddName);
        listView = (ListView) findViewById(R.id.studentListView);
        studentInfoArrayList = new ArrayList<StudentInfo>();
        studentDatabaseAdapter = new StudentDatabaseAdapter(this);
        studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);

        studentAdapter = new StudentAdapter(AddStudent.this, studentInfoArrayList);

        listView.setAdapter(studentAdapter);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = addid.getText().toString();
                name = addName.getText().toString();

                if (id.equals("") || name.equals("")) {
                    Snackbar.make(v, "Fill all the field", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    long lid = studentDatabaseAdapter.StudentInfoInsert(id, name, ClassTypeID);
                    studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);
                    studentAdapter = new StudentAdapter(AddStudent.this, studentInfoArrayList);
                    listView.setAdapter(studentAdapter);
                    Snackbar.make(v, "Student is Added", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final Dialog longD = new Dialog(AddStudent.this);
                longD.setContentView(R.layout.d__class_long_press);
                ImageView close = (ImageView) longD.findViewById(R.id.closeImg);
                Button delete = (Button) longD.findViewById(R.id.deletebtn);
                Button update = (Button) longD.findViewById(R.id.updatebtn);
                longD.setCancelable(false);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longD.dismiss();
                    }

                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
                        builder.setTitle("Delete Class");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure to delete this class ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = String.valueOf(studentAdapter.get_Std_id(position));
                                studentDatabaseAdapter.deleteStudent(id);
                                Snackbar.make(view, "Class is Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);
                                studentAdapter = new StudentAdapter(AddStudent.this, studentInfoArrayList);
                                listView.setAdapter(studentAdapter);
                                dialog.cancel();
                                longD.dismiss();

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


                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        longD.dismiss();
                        dialog.setContentView(R.layout.d__student_data_update);
                        dialog.setCancelable(false);

                        D_cancel = (Button) dialog.findViewById(R.id.cancelbtns);
                        D_save = (Button) dialog.findViewById(R.id.savebtns);
                        classN = (EditText) dialog.findViewById(R.id.classNameETs);
                        descp = (EditText) dialog.findViewById(R.id.classDescriptionETs);



                        classN.setText(String.valueOf(studentAdapter.get_Std_Cid(position)));

                        descp.setText(studentAdapter.get_Std_name(position));
                        descp.setHint("Enter Student Name");
                        classN.setHint("Enter Student ID");
                        D_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        D_save.setText("Update");


                        D_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String Std_ID = classN.getText().toString();
                                String Std_name = descp.getText().toString();

                                if (Std_ID.equals("") || Std_name.equals("")) {
                                    Snackbar.make(v, "Fill all the Fields", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {

                                    String id = String.valueOf(studentAdapter.get_Std_id(position));
                                    studentDatabaseAdapter.updateStudent(Std_ID, Std_name, id);
                                    Snackbar.make(getCurrentFocus(), "Student is Updated", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    studentInfoArrayList = studentDatabaseAdapter.getStudentData(ClassTypeID);
                                    studentAdapter = new StudentAdapter(AddStudent.this, studentInfoArrayList);
                                    listView.setAdapter(studentAdapter);

                                    dialog.dismiss();
                                }
                            }
                        });


                        dialog.show();


                    }


                });

                longD.show();


                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
