package com.example.a1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

public class Present extends Activity implements View.OnClickListener {

    PbAdapter adapter=null;
    static ArrayList<Phonebook> list = new ArrayList<Phonebook>();
    int total=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pb_list);

        SharedPreferences sp=getSharedPreferences("contact",MODE_PRIVATE);
        String str=sp.getString("phone",null);
        if(str!=null) jsonParsing(str);
        Collections.sort(list);

        //LISTVIEW
        final ListView listview = (ListView)findViewById(R.id.pb_listview);

        adapter = new PbAdapter(this,R.layout.pb_item, list);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Next.class);

                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("number", list.get(position).getNumber());
                list.get(position).setFriendly(list.get(position).getFriendly()+1);
                intent.putExtra("friendly", list.get(position).getFriendly());
                total++;
                intent.putExtra("total",total);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        //BUTTON-ADD
        final Button addbt=(Button)findViewById(R.id.add);
        addbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParsing(String json){
        list.clear();
        try{
            JSONObject jobj=new JSONObject(json);
            JSONArray jarray=jobj.getJSONArray("Phonebook");

            for(int i=0; i<jarray.length(); i++){
                JSONObject pobj=jarray.getJSONObject(i);

                Phonebook pb=new Phonebook();
                pb.setName(pobj.getString("name"));
                pb.setNumber(pobj.getString("number"));
                pb.setFriendly(pobj.getInt("friendly"));

                total+=pobj.getInt("friendly");

                list.add(pb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
    }
}