package com.myclasshelper.sujit007.myclasshelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.myclasshelper.sujit007.myclasshelper.ClassInformation.ClassActivity;
import com.myclasshelper.sujit007.myclasshelper.ClassInformation.ClassAdapter;
import com.myclasshelper.sujit007.myclasshelper.ClassInformation.ClassDatabaseAdapter;
import com.myclasshelper.sujit007.myclasshelper.ClassInformation.ClassInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button D_save, D_cancel;

    EditText classN, descp ;
    TextView nam , dets;
    ClassDatabaseAdapter databaseAdapter;
    ArrayList<ClassInfo> classInfoArrayList;
    ClassAdapter classAdapter;
    ListView ClassListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nam = (TextView) findViewById(R.id.nameuser);
        dets = (TextView) findViewById(R.id.desuser);

        final SharedPreferences sharedPreferences = getSharedPreferences("userData" , MODE_PRIVATE);
        String namst , descst ;
        namst = sharedPreferences.getString("name1" , "");
        descst = sharedPreferences.getString("des1", "");

        nam.setText(namst);
        dets.setText(descst);




        final Dialog dialog = new Dialog(MainActivity.this);
        final Dialog licDlg = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        licDlg.setCancelable(false);
        databaseAdapter = new ClassDatabaseAdapter(this);
        classInfoArrayList = new ArrayList<ClassInfo>();
        ClassListView = (ListView) findViewById(R.id.ClassLV);
        classInfoArrayList = databaseAdapter.getClassData();

        classAdapter = new ClassAdapter(MainActivity.this, classInfoArrayList);

        if (classInfoArrayList.size() == 0) {


            final Dialog dgl = new Dialog(MainActivity.this);
            dgl.setCancelable(false);
            dgl.setContentView(R.layout.d__alart2addclass);
            Button gotit = (Button) dgl.findViewById(R.id.gotit);
            gotit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dgl.dismiss();
                }
            });

            dgl.show();
        }

        ClassListView.setAdapter(classAdapter);

        ClassListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();


                int ClassID = classAdapter.get_id(position);
                String ClassName = classAdapter.get_name(position);


                bundle.putString("ClassName" , ClassName);
                bundle.putInt("ClassID", ClassID);
                Intent intent = new Intent(MainActivity.this ,ClassActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });



        ClassListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                final Dialog longD = new Dialog(MainActivity.this);
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

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete Class");
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure to delete this class ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = String.valueOf(classAdapter.get_id(position));
                                databaseAdapter.deleteClass(id);
                                Snackbar.make(view, "Class is Deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                classInfoArrayList = databaseAdapter.getClassData();
                                classAdapter = new ClassAdapter(MainActivity.this, classInfoArrayList);
                                ClassListView.setAdapter(classAdapter);
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
                        dialog.setContentView(R.layout.d__class_data_insert);

                        D_cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                        D_save = (Button) dialog.findViewById(R.id.savebtn);
                        classN = (EditText) dialog.findViewById(R.id.classNameET);
                        descp = (EditText) dialog.findViewById(R.id.classDescriptionET);
                        classN.setText(classAdapter.get_name(position));

                        descp.setText(classAdapter.get_des(position));
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


                                String className = classN.getText().toString();
                                String ClassDetails = descp.getText().toString();

                                if (className.equals("") || ClassDetails.equals("")) {
                                    Snackbar.make(v, "Fill all the Fields", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {

                                    String id = String.valueOf(classAdapter.get_id(position));
                                    databaseAdapter.updateClass(className ,ClassDetails ,id);
                                    Snackbar.make(getCurrentFocus(), "Class is Updated", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    classInfoArrayList = databaseAdapter.getClassData();
                                    classAdapter = new ClassAdapter(MainActivity.this, classInfoArrayList);
                                    ClassListView.setAdapter(classAdapter);

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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.ADD);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                dialog.setContentView(R.layout.d__class_data_insert);

                D_cancel = (Button) dialog.findViewById(R.id.cancelbtn);
                D_save = (Button) dialog.findViewById(R.id.savebtn);
                D_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                D_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        classN = (EditText) dialog.findViewById(R.id.classNameET);
                        descp = (EditText) dialog.findViewById(R.id.classDescriptionET);

                        String className = classN.getText().toString();
                        String ClassDetails = descp.getText().toString();

                        if (className.equals("") || ClassDetails.equals("")) {
                            Snackbar.make(v, "Fill all the Fields", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {


                            long Cid = databaseAdapter.ClassInfoInsert(className, ClassDetails);
                            if (Cid > 0) {

                                classInfoArrayList = databaseAdapter.getClassData();

                                classAdapter = new ClassAdapter(MainActivity.this, classInfoArrayList);
                                Snackbar.make(view, "Class is Saved", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                ClassListView.setAdapter(classAdapter);

                            }
                            dialog.dismiss();
                        }

                        if (classInfoArrayList.size() == 1) {
                            final Dialog dgl2 = new Dialog(MainActivity.this);

                            dgl2.setContentView(R.layout.d__after_adding_new_class);
                            Button gotit = (Button) dgl2.findViewById(R.id.gotit);
                            gotit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dgl2.dismiss();
                                }
                            });

                            dgl2.show();
                        }

                    }
                });

                if (classInfoArrayList.size() == 1) {

                    //                Toast.makeText(getApplicationContext(), "MOM", Toast.LENGTH_LONG).show();
                    licDlg.setContentView(R.layout.d__getlicencekey);
                    ImageView close = (ImageView) licDlg.findViewById(R.id.closeImg);
                    Button actv = (Button) licDlg.findViewById(R.id.savelibtn);
                    final EditText clLiET = (EditText) licDlg.findViewById(R.id.classLicenceET);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            licDlg.dismiss();
                        }

                    });
                    actv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clLiET.getText().toString().equals("1234")) {
                                licDlg.dismiss();
                                dialog.show();
                            } else {
                                Snackbar.make(v, "Insert Valid Key", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
                    licDlg.show();

                } else {


                    dialog.show();


                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
