package com.myclasshelper.sujit007.myclasshelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText name , des;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final SharedPreferences sharedPreferences = getSharedPreferences("userData" , MODE_PRIVATE);
        String nam , desc ;
        nam = sharedPreferences.getString("name1" , "");
        desc = sharedPreferences.getString("des1", "");
        save = (Button) findViewById(R.id.saveuser);

        if(nam.equals("") || desc.equals("") ){
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = (EditText) findViewById(R.id.userName);
                    des = (EditText) findViewById(R.id.userdes);

                    if(name.equals("")|| des.equals("")){
                        Snackbar.make(v, "Fill all the Fields", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name1" , name.getText().toString());
                        editor.putString("des1" , des.getText().toString());
                        editor.commit();
                        startActivity(new Intent(RegisterActivity.this , MainActivity.class));
                        finish();
                    }



                }
            });

        }
        else {
            startActivity(new Intent(RegisterActivity.this , MainActivity.class));
            finish();
        }




    }
}
