package com.example.a1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Present extends Activity implements View.OnClickListener {

    PbAdapter adapter=null;
    ArrayList<Phonebook> list=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pb_list);

        //BUTTON
        Button addbt=(Button)findViewById(R.id.btn);
        addbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
            }
        });

        //LISTVIEW
        ListView listview = (ListView)findViewById(R.id.pb_listview);
        list = new ArrayList<Phonebook>();

        String json=getJsonString();
        Phonebook pb=new Phonebook(json);
        for (int i = 0; i < pb.getList().size(); i++) {
            list.add(pb.getList().get(i));
        }

        adapter = new PbAdapter(this,R.layout.pb_item, list);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Next.class);

                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("number", list.get(position).getNumber());
                startActivity(intent);
            }
        });
    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("db.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    @Override
    public void onClick(View view) {

    }
}
