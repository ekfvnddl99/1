package com.example.a1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Add extends Activity implements View.OnClickListener {
    ArrayList<Phonebook> list=new ArrayList<Phonebook>();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_click);

        Button okaybt=(Button)findViewById(R.id.okay);
        okaybt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditText editname=(EditText)findViewById(R.id.add_name);
                EditText editnum=(EditText)findViewById(R.id.add_number);

                String uname=editname.getText().toString();
                String unum=editnum.getText().toString();


                list.add(new Phonebook(uname,unum));

                Intent intent=new Intent(getApplicationContext(), Present.class);
                intent.putExtra("list", list);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}